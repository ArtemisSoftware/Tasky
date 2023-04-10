package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.tasky.agenda.data.remote.dto.AttendeeDto
import com.artemissoftware.tasky.agenda.domain.models.Attendee

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        id = attendeeProfile.userId,
        email = attendeeProfile.email,
        fullName = attendeeProfile.fullName,
    )
}
