package com.artemissoftware.tasky.agenda.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: String,
    @SerializedName("isDone")
    val isDone: Boolean,
    @SerializedName("remindAt")
    val remindAt: Long,
    @SerializedName("time")
    val time: Long,
    @SerializedName("title")
    val title: String
)