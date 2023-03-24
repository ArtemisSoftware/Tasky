package com.artemissoftware.core.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.artemissoftware.core.data.database.entities.EventAttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.PhotoEntity

data class EventAndSyncState(
    @Embedded
    val event: EventEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val eventSyncEntity: EventSyncEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "eventId",
    )
    val attendees: List<EventAttendeeEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "eventId",
    )
    val photos: List<PhotoEntity>,
)
