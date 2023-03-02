package com.artemissoftware.tasky.agenda.data.remote.dto


import com.squareup.moshi.Json

data class ReminderDto(
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "remindAt")
    val remindAt: Long,
    @field:Json(name = "time")
    val time: Long,
    @field:Json(name = "title")
    val title: String
)