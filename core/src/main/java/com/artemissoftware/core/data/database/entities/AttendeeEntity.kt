package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("eventId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["eventId"])],
)
data class AttendeeEntity(
    val eventId: String,
    @PrimaryKey
    val userId: String,
    val email: String,
    val fullName: String,
    val isGoing: Boolean,
)
