package com.artemissoftware.tasky.util

import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto
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
}
