package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.EventAttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.PhotoEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.EventCreateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventUpdateBodyDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.models.Photo

fun EventDto.toEntity(): EventEntity {
    return EventEntity(
        id = id,
        description = description,
        from = from,
        host = host,
        isUserEventCreator = isUserEventCreator,
        remindAt = remindAt,
        title = title,
        to = to,
    )
}

fun EventAttendeeEntity.toAttendee(): Attendee {
    return Attendee(
        id = userId,
        fullName = fullName,
        email = email,
        remindAt = remindAt
    )
}

fun PhotoEntity.toPhoto(): Photo {
    return Photo(
        key = key,
        url = url,
        local = null, // TODO: must get the File if it exists
    )
}

fun EventAndSyncState.toAgendaItem(): AgendaItem.Event {
    return AgendaItem.Event(
        title = this.event.title,
        description = this.event.description,
        id = this.event.id,
        host = this.event.host,
        remindAt = this.event.remindAt.toLocalDateTime(),
        to = this.event.to.toLocalDateTime(),
        photos = photos.map { it.toPhoto() },
        attendees = attendees.map { it.toAttendee() },
        from = this.event.from.toLocalDateTime(),
        isGoing = true, // TODO: where do I get this value? need to check
        syncState = this.eventSyncEntity.syncType,
    )
}

fun AgendaItem.Event.toEntity(): EventEntity {
    return EventEntity(
        title = title,
        description = this.description ?: "",
        id = this.id,
        remindAt = remindAt.toLong(),
        to = to.toLong(),
        from = from.toLong(),
        isUserEventCreator = true, // TODO: not sure about it
        host = host
    )
}
fun AgendaItem.Event.toPhotoEntities(): List<PhotoEntity> {

    return this.photos.map {  photo ->
        PhotoEntity(
            eventId = this.id,
            key = photo.key,
            url = photo.url,
            local = photo.local?.absolutePath

        )
    }
}

fun AgendaItem.Event.toAttendeeEntities(): List<EventAttendeeEntity> {

    return this.attendees.map {  attendee ->
        EventAttendeeEntity(
            eventId = this.id,
            fullName = attendee.fullName,
            userId = attendee.id,
            email = attendee.email,
            remindAt = attendee.remindAt,
            isGoing = true // TODO: not sure about it
        )
    }
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
        deletedPhotoKeys = this.deletedPhotosIds,
        isGoing = isGoing
    )
}
