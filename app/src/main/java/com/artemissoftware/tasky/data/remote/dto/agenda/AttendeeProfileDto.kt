package com.artemissoftware.tasky.data.remote.dto.agenda


import com.google.gson.annotations.SerializedName

data class AttendeeProfileDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("userId")
    val userId: String
)