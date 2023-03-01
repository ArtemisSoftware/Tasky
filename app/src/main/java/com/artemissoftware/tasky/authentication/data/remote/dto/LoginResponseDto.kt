package com.artemissoftware.tasky.authentication.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userId")
    val userId: String
)