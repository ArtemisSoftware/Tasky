package com.artemissoftware.core.data.remote.dto.authentication


import com.squareup.moshi.Json

data class LoginBodyDto(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "password")
    val password: String
)