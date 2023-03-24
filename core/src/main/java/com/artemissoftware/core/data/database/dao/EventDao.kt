package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.EventAttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.PhotoEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState

@Dao
interface EventDao {

    @Query("SELECT * FROM eventEntity WHERE id = :id")
    fun getEventAndSyncState(id: String): EventAndSyncState?

    @Upsert
    fun upsert(eventEntity: EventEntity)

    @Upsert
    fun upsert(eventAttendeeEntities: List<EventAttendeeEntity>)

    @Upsert
    fun upsert(photoEntities: List<PhotoEntity>)

    @Upsert
    suspend fun upsertEventSync(eventSyncEntity: EventSyncEntity)

    @Transaction
    suspend fun upsertSyncStateAndEvent(eventEntity: EventEntity, photoEntities: List<PhotoEntity>, eventAttendeeEntities: List<EventAttendeeEntity>, eventSyncEntity: EventSyncEntity) {
        upsert(eventEntity)
        upsert(photoEntities)
        upsert(eventAttendeeEntities)
        upsertEventSync(eventSyncEntity)
    }

    @Query("DELETE FROM eventEntity WHERE id = :id")
    suspend fun deleteEvent(id: String)

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, eventSyncEntity: EventSyncEntity) {
        deleteEvent(id)
        upsertEventSync(eventSyncEntity)
    }
}