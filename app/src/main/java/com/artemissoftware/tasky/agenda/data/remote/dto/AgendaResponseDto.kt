package com.artemissoftware.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class AgendaResponseDto(
    @field:Json(name = "events")
    val events: List<EventDto> = emptyList(),
    @field:Json(name = "tasks")
    val tasks: List<TaskDto> = emptyList(),
    @field:Json(name = "reminders")
    val reminders: List<ReminderDto> = emptyList()
)
