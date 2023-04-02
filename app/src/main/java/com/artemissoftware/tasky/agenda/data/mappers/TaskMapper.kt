package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.relations.TaskAndSyncState
import com.artemissoftware.core.data.database.util.getValidNotification
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import java.time.Duration

fun TaskDto.toEntity(notifications: List<NotificationWarningEntity>): TaskEntity?  {

    return notifications.getValidNotification(remindAt = remindAt.toLocalDateTime(), time = time.toLocalDateTime())?.let { notification ->
        TaskEntity(
            title = title,
            description = description,
            id = id,
            remindAt = remindAt,
            time = time,
            isDone = isDone,
            notificationId = notification.id,
        )
    }
}

fun TaskAndSyncState.toAgendaItem(): AgendaItem.Task {
    return AgendaItem.Task(
        title = this.task.title,
        description = this.task.description,
        id = this.task.id,
        remindAt = this.task.remindAt.toLocalDateTime(),
        time = this.task.time.toLocalDateTime(),
        syncState = this.syncState.syncType,
        isDone = this.task.isDone,
        notification = this.notification.toNotification(),
    )
}

fun AgendaItem.Task.toEntity(): TaskEntity {
    return TaskEntity(
        title = itemTitle,
        description = itemDescription,
        id = this.id,
        remindAt = remindAt.toLong(),
        time = time.toLong(),
        isDone = isDone,
        notificationId = notification.id,
    )
}

fun AgendaItem.Task.toDto(): TaskDto {
    return TaskDto(
        title = itemTitle,
        description = itemDescription,
        id = this.id,
        remindAt = remindAt.toLong(),
        time = time.toLong(),
        isDone = isDone,
    )
}
