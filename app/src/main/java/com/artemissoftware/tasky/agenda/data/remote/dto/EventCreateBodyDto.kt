package com.artemissoftware.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class EventCreateBodyDto(
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "from")
    val from: Int,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "remindAt")
    val remindAt: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "to")
    val to: Int,
    @field:Json(name = "attendeeIds")
    val attendeeIds: List<String>,
)
