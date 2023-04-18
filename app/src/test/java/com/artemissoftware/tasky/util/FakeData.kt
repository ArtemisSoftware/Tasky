package com.artemissoftware.tasky.util

import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.agenda.data.remote.dto.AttendeeDto
import com.artemissoftware.tasky.agenda.data.remote.dto.AttendeeProfileDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventAttendeeDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.core.data.remote.dto.authentication.LoginResponseDto
import java.time.LocalDateTime

object FakeData {

    val loginResponseDto = LoginResponseDto(
        fullName = "Bruce Wayne",
        token = "IamBatman",
        userId = "DarkKnight",
    )

    val user = User(
        fullName = "Bruce Wayne",
        token = "IamBatman",
        id = "DarkKnight",
    )

    val reminder = AgendaItem.Reminder(
        id = "bat",
        title = "batarang",
        description = "Build a new gadget",
        remindAt = LocalDateTime.now().minusMinutes(10),
        time = LocalDateTime.now(),
    )

    val task = AgendaItem.Task(
        id = "taskId",
        title = "Riddle me this",
        description = "No riddles today",
        remindAt = LocalDateTime.now().minusMinutes(10),
        time = LocalDateTime.now(),
    )

    val attendeeProfileDto = AttendeeProfileDto(
        email = "batman@email.com",
        fullName = "Bruce Wayne",
        userId = "123",
    )

    val attendeeDto = AttendeeDto(
        attendeeProfile = attendeeProfileDto,
        doesUserExist = true,
    )

    val attendeeEntity = AttendeeEntity(
        eventId = "eventId",
        userId = "123",
        email = "batman@email.com",
        fullName = "Bruce Wayne",
        isGoing = true,
    )

    val eventAttendeeDto = EventAttendeeDto(
        email = "batman@email.com",
        eventId = "eventId",
        fullName = "Bruce Wayne",
        isGoing = true,
        remindAt = 1000L,
        userId = "123",
    )
}
