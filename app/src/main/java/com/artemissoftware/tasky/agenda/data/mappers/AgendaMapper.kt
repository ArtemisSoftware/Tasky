package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.SyncType
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun ReminderDto.toEntity(): ReminderEntity {
    return ReminderEntity(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time,
        syncType = SyncType.SYNCED
    )
}

fun ReminderEntity.toAgendaItem(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt.toLocalDateTime(),
        time = time.toLocalDateTime()
    )
}

fun AgendaItem.Reminder.toEntity(syncType: SyncType): ReminderEntity {

    return ReminderEntity(
        title = title,
        description = description,
        id = this.id,
        remindAt = remindAt.toLong(),
        time = time.toLong(),
        syncType = syncType
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