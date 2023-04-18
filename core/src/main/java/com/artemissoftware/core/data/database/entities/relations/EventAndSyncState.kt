package com.artemissoftware.core.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.PictureEntity

data class EventAndSyncState(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventId",
    )
    val attendees: List<AttendeeEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val pictures: List<PictureEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val syncState: EventSyncEntity,
)
