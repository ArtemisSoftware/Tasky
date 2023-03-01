package com.artemissoftware.tasky.data.remote.dto.agenda


import com.google.gson.annotations.SerializedName

data class ReminderDto(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: String,
    @SerializedName("remindAt")
    val remindAt: Long,
    @SerializedName("time")
    val time: Long,
    @SerializedName("title")
    val title: String
)