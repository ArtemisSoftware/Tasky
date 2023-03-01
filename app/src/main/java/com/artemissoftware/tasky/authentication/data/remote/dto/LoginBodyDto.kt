package com.artemissoftware.tasky.authentication.data.remote.dto


import com.google.gson.annotations.SerializedName

data class LoginBodyDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)