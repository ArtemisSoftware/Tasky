package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.artemissoftware.core.data.database.types.PictureType

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class PictureEntity(
    @PrimaryKey
    val id: String,
    val source: String,
    val type: PictureType,
    val eventId: String,
)
