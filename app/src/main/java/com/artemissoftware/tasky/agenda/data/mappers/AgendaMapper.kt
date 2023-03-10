package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun ReminderDto.toEntity(): ReminderEntity {
    return ReminderEntity(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time
    )
}

fun ReminderEntity.toAgendaItem(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time
    )
}

fun AgendaItem.Reminder.toEntity(id: String): ReminderEntity {

    return ReminderEntity(
        title = title,
        description = description,
        id = this.id ?: id,
        remindAt = remindAt,
        time = time
    )
}
