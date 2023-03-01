package com.artemissoftware.tasky.data.remote.dto.agenda


import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("key")
    val key: String,
    @SerializedName("url")
    val url: String
)