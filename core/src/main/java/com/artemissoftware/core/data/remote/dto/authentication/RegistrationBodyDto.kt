package com.artemissoftware.core.data.remote.dto.authentication


import com.squareup.moshi.Json

data class RegistrationBodyDto(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "fullName")
    val fullName: String,
    @field:Json(name = "password")
    val password: String
)