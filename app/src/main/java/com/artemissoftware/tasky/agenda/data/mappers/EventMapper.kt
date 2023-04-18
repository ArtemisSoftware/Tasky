package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun AgendaItem.Event.toEntity(): EventEntity {
    return EventEntity(
        title = title,
        description = this.description ?: "",
        id = this.id,
        remindAt = remindAt.toLong(),
        endDate = to.toLong(),
        startDate = from.toLong(),
        isUserEventCreator = isUserEventCreator,
        hostId = hostId,
    )
}

fun EventAndSyncState.toAgendaItem(): AgendaItem.Event {
    return AgendaItem.Event(
        title = this.event.title,
        description = this.event.description,
        id = this.event.id,
        hostId = this.event.hostId,
        remindAt = this.event.remindAt.toLocalDateTime(),
        to = this.event.endDate.toLocalDateTime(),
        from = this.event.startDate.toLocalDateTime(),
        attendees = attendees.map { it.toAttendee() },
        pictures = pictures.map { it.toPicture() }
    )
}
