package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String? = null,
    val remindAt: Long,
    val time: Long,
    val isDone: Boolean,
    val notificationId: Int,
)
