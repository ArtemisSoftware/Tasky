package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.core.data.database.entities.AgendaItemEntity
import com.artemissoftware.core.domain.models.agenda.AgendaItemType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

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

fun AgendaItemEntity.toAgendaItem(): AgendaItem {
    return AgendaItem(
        title = title,
        description = description,
        id = id,
        remindAt = remindAt,
        time = time,
        type = type
    )
}

fun AgendaItem.toEntity(id: String): AgendaItemEntity {
    return AgendaItemEntity(
        title = title,
        description = description,
        id = this.id?: id,
        remindAt = remindAt,
        time = time,
        type = type
    )
}