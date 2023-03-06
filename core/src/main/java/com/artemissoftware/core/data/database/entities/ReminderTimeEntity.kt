package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReminderTimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val description: String,
    val durationInMinutes: Long
)
