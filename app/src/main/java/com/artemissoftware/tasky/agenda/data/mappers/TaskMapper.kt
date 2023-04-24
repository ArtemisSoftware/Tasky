package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.data.database.entities.relations.ReminderAndSyncState
import com.artemissoftware.core.data.database.entities.relations.TaskAndSyncState
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun TaskDto.toEntity(): TaskEntity {
    return TaskEntity(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time,
        isDone = isDone,
    )
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

fun TaskDto.toTask(): AgendaItem.Task {
    return AgendaItem.Task(
        title = this.title,
        description = this.description,
        id = this.id,
        remindAt = this.remindAt.toLocalDateTime(),
        time = this.time.toLocalDateTime(),
        syncState = SyncType.SYNCED,
        isDone = isDone,
    )
}

fun AgendaItem.Task.toTaskAndSyncState(): TaskAndSyncState {
    return TaskAndSyncState(
        task = this.toEntity(),
        syncState = TaskSyncEntity(id = this.id, syncType = SyncType.SYNCED),
    )
}
