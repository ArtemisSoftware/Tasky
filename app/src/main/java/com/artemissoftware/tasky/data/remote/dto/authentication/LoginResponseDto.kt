package com.artemissoftware.tasky.data.remote.dto.authentication


import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userId")
    val userId: String
)