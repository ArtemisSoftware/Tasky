package com.artemissoftware.tasky.agenda.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("key")
    val key: String,
    @SerializedName("url")
    val url: String
)