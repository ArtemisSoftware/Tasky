package com.artemissoftware.core.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("message")
    val message: String
)