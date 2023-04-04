package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.relations.ReminderAndSyncState
import com.artemissoftware.core.data.database.util.getValidNotification
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Notification

fun ReminderDto.toEntity(notifications: List<NotificationWarningEntity>): ReminderEntity? {
    return notifications.getValidNotification(remindAt = remindAt.toLocalDateTime(), time = time.toLocalDateTime())?.let { notification ->

        ReminderEntity(
            title = title,
            description = description,
            id = id,
            remindAt = remindAt,
            time = time,
            notificationId = notification.id,
        )
    }
}

fun ReminderAndSyncState.toAgendaItem(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        title = this.reminder.title,
        description = this.reminder.description,
        id = this.reminder.id,
        remindAt = this.reminder.remindAt.toLocalDateTime(),
        time = this.reminder.time.toLocalDateTime(),
        syncState = this.syncState.syncType,
        notification = this.notification.toNotification(),
    )
}

fun AgendaItem.Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        title = itemTitle,
        description = itemDescription,
        id = this.id,
        remindAt = remindAt.toLong(),
        time = time.toLong(),
        notificationId = notification.id,
    )
}

fun AgendaItem.Reminder.toDto(): ReminderDto {
    return ReminderDto(
        title = itemTitle,
        description = itemDescription,
        id = this.id,
        remindAt = remindAt.toLong(),
        time = time.toLong(),
    )
}

fun ReminderDto.toReminder(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        title = this.title,
        description = this.description,
        id = this.id,
        remindAt = this.remindAt.toLocalDateTime(),
        time = this.time.toLocalDateTime(),
        syncState = SyncType.SYNCED,
        notification = Notification(2, 30L, "", isDefault = false) // TODO: remover isto
    )
}
