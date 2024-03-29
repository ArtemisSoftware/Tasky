package com.artemissoftware.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class EventAttendeeDto(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "eventId")
    val eventId: String,
    @field:Json(name = "fullName")
    val fullName: String,
    @field:Json(name = "isGoing")
    val isGoing: Boolean,
    @field:Json(name = "remindAt")
    val remindAt: Long,
    @field:Json(name = "userId")
    val userId: String,
)
