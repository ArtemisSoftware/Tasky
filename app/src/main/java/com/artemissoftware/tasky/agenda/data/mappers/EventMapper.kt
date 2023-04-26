package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.EventCreateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventUpdateBodyDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

fun AgendaItem.Event.toEventEntity(): EventEntity {
    return EventEntity(
        title = title,
        description = this.description ?: "",
        id = this.id,
        remindAt = remindAt.toLong(),
        endDate = to.toLong(),
        startDate = from.toLong(),
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
        pictures = pictures.map { it.toPicture() },
        syncState = this.syncState.syncType,
    )
}

fun AgendaItem.Event.toCreateBodyDto(): EventCreateBodyDto {
    return EventCreateBodyDto(
        title = title,
        description = description ?: "",
        id = this.id,
        remindAt = remindAt.toLong(),
        from = from.toLong(),
        to = to.toLong(),
        attendeeIds = this.attendees.map { it.id },
    )
}

fun AgendaItem.Event.toUpdateBodyDto(): EventUpdateBodyDto {
    return EventUpdateBodyDto(
        title = title,
        description = description ?: "",
        id = this.id,
        remindAt = remindAt.toLong(),
        from = from.toLong(),
        to = to.toLong(),
        attendeeIds = this.attendees.map { it.id },
        deletedPhotoKeys = this.deletedPictures,
        isGoing = isGoing,
    )
}

fun EventDto.toEvent(): AgendaItem.Event {
    return AgendaItem.Event(
        title = this.title,
        description = this.description,
        id = this.id,
        remindAt = this.remindAt.toLocalDateTime(),
        from = this.from.toLocalDateTime(),
        to = this.to.toLocalDateTime(),
        pictures = this.photos.map { it.toPicture() },
        attendees = this.attendees.map { it.toAttendee() },
        hostId = host,
        syncState = SyncType.SYNCED,
    )
}

fun EventDto.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        remindAt = this.remindAt,
        startDate = this.from,
        endDate = this.to,
        hostId = host,
    )
}

fun AgendaItem.Event.toEventAndSyncState(): EventAndSyncState {
    return EventAndSyncState(
        event = this.toEventEntity(),
        attendees = this.attendees.map { it.toEntity(eventId = this.id) },
        pictures = this.pictures.map { it.toEntity(eventId = this.id) },
        syncState = EventSyncEntity(id = this.id, syncType = SyncType.SYNCED),
    )
}
