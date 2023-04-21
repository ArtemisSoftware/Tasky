package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState
import com.artemissoftware.core.util.extensions.toEndOfDayEpochMilli
import com.artemissoftware.core.util.extensions.toStartOfDayEpochMilli
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.time.LocalDate

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
    fun upsert(eventEntity: List<EventEntity>)

    @Upsert
    suspend fun upsertEventSync(eventSyncEntity: EventSyncEntity)

    @Upsert
    suspend fun upsertEventSync(eventSyncEntity: List<EventSyncEntity>)

    @Transaction
    suspend fun upsertSyncStateAndEvent(eventEntity: EventEntity, eventSyncEntity: EventSyncEntity) {
        upsert(eventEntity)
        upsertEventSync(eventSyncEntity)
    }

    @Transaction
    suspend fun upsertSyncStateAndEvents(events: List<EventEntity>, eventsSyncType: List<EventSyncEntity>) {
        upsert(events)
        upsertEventSync(eventsSyncType)
    }

    @Query("DELETE FROM eventEntity WHERE id = :id")
    suspend fun deleteEvent(id: String)

    @Delete
    suspend fun deleteAllEvents(events: List<EventEntity>)

    @Query("DELETE FROM eventSyncEntity WHERE id IN (:idList)")
    suspend fun deleteSyncState(idList: List<String>)

    @Transaction
    suspend fun deleteEventsAndSyncState(date: LocalDate): List<String> {
        val initialDate = date.toStartOfDayEpochMilli()
        val endDate = date.toEndOfDayEpochMilli()

        val events = getEvents(initialDate = initialDate, endDate = endDate).first()
        deleteAllEvents(events = events.map { it.event })
        deleteSyncState(events.map { it.event.id })

        return events.map { it.event.id }
    }

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, eventSyncEntity: EventSyncEntity) {
        deleteEvent(id)
        upsertEventSync(eventSyncEntity)
    }

    @Transaction
    @Query("SELECT * FROM eventEntity WHERE startDate >= :currentTime")
    suspend fun getEventsToSetAlarm(currentTime: Long): List<EventAndSyncState>
}
