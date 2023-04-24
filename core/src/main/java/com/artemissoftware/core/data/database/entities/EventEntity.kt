package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val startDate: Long,
    val hostId: String,
    val remindAt: Long,
    val title: String,
    val endDate: Long,
)
