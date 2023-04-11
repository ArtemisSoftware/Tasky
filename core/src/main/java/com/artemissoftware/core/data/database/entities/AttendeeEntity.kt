package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    // TODO: should relate to event via foreign Keys. Uncomment the lines below
//    foreignKeys = [
//        ForeignKey(
//            entity = EventEntity::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("eventId"),
//            onDelete = ForeignKey.CASCADE,
//        ),
//    ],
)
data class AttendeeEntity(
    val eventId: String,
    @PrimaryKey
    val userId: String,
    val email: String,
    val fullName: String,
    val isGoing: Boolean,
)
