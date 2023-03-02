package com.artemissoftware.tasky.agenda.data.remote.dto


import com.squareup.moshi.Json

data class AttendeeProfileDto(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "fullName")
    val fullName: String,
    @field:Json(name = "userId")
    val userId: String
)