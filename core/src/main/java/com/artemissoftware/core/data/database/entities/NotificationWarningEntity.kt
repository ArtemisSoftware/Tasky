package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationWarningEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val description: String,
    val minutesBefore: Long
)
