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
data class PhotoEntity(
    @PrimaryKey
    val key: String,
    val url: String,
    val local: String? = null,
    val eventId: String,
)
