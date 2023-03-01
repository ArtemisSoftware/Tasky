package com.artemissoftware.tasky.data.remote.dto.authentication


import com.google.gson.annotations.SerializedName

data class LoginBodyDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)