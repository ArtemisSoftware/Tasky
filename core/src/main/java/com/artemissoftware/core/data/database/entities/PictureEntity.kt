package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemissoftware.core.data.database.types.PictureType

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
data class PictureEntity(
    @PrimaryKey
    val id: String,
    val source: String,
    val type: PictureType,
    val eventId: String,
)
