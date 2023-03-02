package com.artemissoftware.tasky.data.remote.dto.agenda


import com.google.gson.annotations.SerializedName

data class AttendeeDto(
    @SerializedName("attendee")
    val attendeeProfile: AttendeeProfileDto,
    @SerializedName("doesUserExist")
    val doesUserExist: Boolean
)