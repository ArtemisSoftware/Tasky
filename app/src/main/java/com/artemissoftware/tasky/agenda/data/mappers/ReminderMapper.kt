package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState
import com.artemissoftware.core.data.database.entities.relations.ReminderAndSyncState
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun ReminderDto.toEntity(): ReminderEntity {
    return ReminderEntity(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time,
    )
}

fun ReminderAndSyncState.toAgendaItem(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        title = this.reminder.title,
        description = this.reminder.description,
        id = this.reminder.id,
        remindAt = this.reminder.remindAt.toLocalDateTime(),
        time = this.reminder.time.toLocalDateTime(),
        syncState = this.syncState.syncType,
    )
}

fun AgendaItem.Reminder.toEntity(): ReminderEntity {
    return ReminderEntity(
        title = title,
        description = description,
        id = this.id,
        remindAt = remindAt.toLong(),
        time = time.toLong(),
    )
}

fun AgendaItem.Reminder.toDto(): ReminderDto {
    return ReminderDto(
        title = title,
        description = description,
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
    )
}

fun AgendaItem.Reminder.toReminderAndSyncState(): ReminderAndSyncState {
    return ReminderAndSyncState(
        reminder = this.toEntity(),
        syncState = ReminderSyncEntity(id = this.id, syncType = SyncType.SYNCED),
    )
}
