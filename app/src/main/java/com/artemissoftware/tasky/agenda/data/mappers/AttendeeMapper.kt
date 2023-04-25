package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.core.util.extensions.toLocalDateTime
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.AttendeeDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventAttendeeDto
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import java.time.LocalDateTime

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        id = attendeeProfile.userId,
        email = attendeeProfile.email,
        fullName = attendeeProfile.fullName,
        isGoing = true,
        remindAt = LocalDateTime.now(),
    )
}

fun Attendee.toEntity(eventId: String): AttendeeEntity {
    return AttendeeEntity(
        eventId = eventId,
        userId = id,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toLong(),
    )
}

fun EventAttendeeDto.toEntity(): AttendeeEntity {
    return AttendeeEntity(
        eventId = eventId,
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt,
    )
}

fun AttendeeEntity.toAttendee(): Attendee {
    return Attendee(
        fullName = fullName,
        id = userId,
        email = email,
        isGoing = isGoing,
        remindAt = remindAt.toLocalDateTime(),
    )
}

fun EventAttendeeDto.toAttendee(): Attendee {
    return Attendee(
        id = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toLocalDateTime(),
    )
}
