package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val from: Long,
    val hostId: String,
    val isUserEventCreator: Boolean,
    val remindAt: Long,
    val title: String,
    val to: Long,
)
