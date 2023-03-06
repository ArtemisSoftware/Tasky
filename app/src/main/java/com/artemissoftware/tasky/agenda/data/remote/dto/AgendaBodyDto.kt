package com.artemissoftware.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class AgendaBodyDto(
    @field:Json(name = "deletedEventIds")
    val deletedEventIds: List<String> = emptyList(),
    @field:Json(name = "deletedTaskIds")
    val deletedTaskIds: List<String> = emptyList(),
    @field:Json(name = "deletedReminderIds")
    val deletedReminderIds: List<String> = emptyList()
)