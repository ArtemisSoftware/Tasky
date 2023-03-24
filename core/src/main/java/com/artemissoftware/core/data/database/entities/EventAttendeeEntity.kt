package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
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
)
data class EventAttendeeEntity(
    val eventId: String,
    @PrimaryKey
    val userId: String,
    val email: String,
    val fullName: String,
    val isGoing: Boolean,
    val remindAt: Long,
    val time: Long,
)
