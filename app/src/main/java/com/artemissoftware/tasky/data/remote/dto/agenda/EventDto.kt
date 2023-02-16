package com.artemissoftware.tasky.data.remote.dto.agenda


import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("from")
    val from: Long,
    @SerializedName("host")
    val host: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isUserEventCreator")
    val isUserEventCreator: Boolean,
    @SerializedName("remindAt")
    val remindAt: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("to")
    val to: Long,
    @SerializedName("attendees")
    val attendees: List<EventAttendeeDto>,
    @SerializedName("photos")
    val photos: List<PhotoDto>
)
