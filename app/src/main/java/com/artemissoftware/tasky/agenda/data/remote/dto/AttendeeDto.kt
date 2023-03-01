package com.artemissoftware.tasky.agenda.data.remote.dto


import com.google.gson.annotations.SerializedName

data class AttendeeDto(
    @SerializedName("attendee")
    val attendeeProfile: AttendeeProfileDto,
    @SerializedName("doesUserExist")
    val doesUserExist: Boolean
)