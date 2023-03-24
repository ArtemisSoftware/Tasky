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
    fun upsert(eventAttendeeEntity: EventAttendeeEntity)

    @Upsert
    fun upsert(photoEntity: PhotoEntity)

    @Upsert
    suspend fun upsertEventSync(eventSyncEntity: EventSyncEntity)

    @Transaction
    suspend fun upsertSyncStateAndTask(eventEntity: EventEntity, eventSyncEntity: EventSyncEntity) {
        upsert(eventEntity)
        upsertEventSync(eventSyncEntity)
    }

    @Transaction
    suspend fun upsertSyncStateAndTask(eventAttendeeEntity: EventAttendeeEntity, eventSyncEntity: EventSyncEntity) {
        upsert(eventAttendeeEntity)
        upsertEventSync(eventSyncEntity)
    }

    @Transaction
    suspend fun upsertSyncStateAndTask(photoEntity: PhotoEntity, eventSyncEntity: EventSyncEntity) {
        upsert(photoEntity)
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