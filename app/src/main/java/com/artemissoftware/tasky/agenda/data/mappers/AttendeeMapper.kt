package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.tasky.agenda.data.remote.dto.AttendeeDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventAttendeeDto
import com.artemissoftware.tasky.agenda.domain.models.Attendee

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        id = attendeeProfile.userId,
        email = attendeeProfile.email,
        fullName = attendeeProfile.fullName,
        isGoing = true,
    )
}

fun Attendee.toEntity(eventId: String): AttendeeEntity {
    return AttendeeEntity(
        eventId = eventId,
        userId = id,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
    )
}

fun EventAttendeeDto.toEventEntity(): AttendeeEntity {
    return AttendeeEntity(
        eventId = eventId,
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
    )
}

fun AttendeeEntity.toAttendee(): Attendee {
    return Attendee(
        fullName = fullName,
        id = userId,
        email = email,
        isGoing = isGoing,
    )
}

fun EventAttendeeDto.toAttendee(): Attendee {
    return Attendee(
        id = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
    )
}
