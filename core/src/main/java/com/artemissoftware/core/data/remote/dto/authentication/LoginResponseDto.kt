package com.artemissoftware.core.data.remote.dto.authentication

import com.squareup.moshi.Json

data class LoginResponseDto(
    @field:Json(name = "fullName")
    val fullName: String,
    @field:Json(name = "token")
    val token: String,
    @field:Json(name = "userId")
    val userId: String
)