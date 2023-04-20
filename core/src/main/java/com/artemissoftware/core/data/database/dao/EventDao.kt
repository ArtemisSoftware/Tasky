package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState
import com.artemissoftware.core.domain.SyncType
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM eventEntity WHERE id = :id")
    suspend fun getEventAndSyncState(id: String): EventAndSyncState?

    @Transaction
    @Query("SELECT * FROM eventEntity WHERE startDate >= :initialDate AND startDate < :endDate")
    fun getEvents(initialDate: Long, endDate: Long): Flow<List<EventAndSyncState>>

    @Transaction
    @Query("SELECT * FROM eventEntity")
    fun getEvents(): Flow<List<EventAndSyncState>>

    @Upsert
    fun upsert(eventEntity: EventEntity)

    @Upsert
    suspend fun upsertEventSync(eventSyncEntity: EventSyncEntity)

    @Transaction
    suspend fun upsertSyncStateAndEvent(eventEntity: EventEntity, eventSyncEntity: EventSyncEntity) {
        upsert(eventEntity)
        upsertEventSync(eventSyncEntity)
    }

    @Query("DELETE FROM eventEntity WHERE id = :id")
    suspend fun deleteEvent(id: String)

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, eventSyncEntity: EventSyncEntity) {
        deleteEvent(id)
        upsertEventSync(eventSyncEntity)
    }

    @Transaction
    @Query("SELECT * FROM eventEntity WHERE startDate >= :currentTime")
    suspend fun getEventsToSetAlarm(currentTime: Long): List<EventAndSyncState>

    @Query("SELECT * FROM eventSyncEntity syncType IN (:types)")
    suspend fun getEventsToSync(types: Array<SyncType> = arrayOf(SyncType.CREATE, SyncType.UPDATE, SyncType.DELETE)): List<EventSyncEntity>
}
