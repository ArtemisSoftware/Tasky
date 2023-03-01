package com.artemissoftware.tasky.authentication.data.remote.dto


import com.google.gson.annotations.SerializedName

data class RegistrationBodyDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("password")
    val password: String
)