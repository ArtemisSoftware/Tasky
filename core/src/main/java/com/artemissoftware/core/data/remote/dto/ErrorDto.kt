package com.artemissoftware.core.data.remote.dto


import com.squareup.moshi.Json

data class ErrorDto(
    @field:Json(name = "message")
    val message: String
)