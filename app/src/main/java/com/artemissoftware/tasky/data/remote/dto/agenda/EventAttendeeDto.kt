package com.artemissoftware.tasky.data.remote.dto.agenda


import com.google.gson.annotations.SerializedName

data class EventAttendeeDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("isGoing")
    val isGoing: Boolean,
    @SerializedName("remindAt")
    val remindAt: Long,
    @SerializedName("userId")
    val userId: String
)