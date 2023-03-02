package com.artemissoftware.tasky.agenda.data.remote.dto

import com.squareup.moshi.Json

data class AttendeeDto(
    @field:Json(name = "attendee")
    val attendeeProfile: AttendeeProfileDto,
    @field:Json(name = "doesUserExist")
    val doesUserExist: Boolean
)