package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.core.data.database.entities.AgendaItemEntity
import com.artemissoftware.core.domain.models.agenda.AgendaItemType

fun ReminderDto.toAgendaItemEntity(): AgendaItemEntity {
    return AgendaItemEntity(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time,
        type = AgendaItemType.REMINDER
    )
}