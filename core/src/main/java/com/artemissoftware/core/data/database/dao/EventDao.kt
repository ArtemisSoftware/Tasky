package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.relations.EventAndSyncState

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM eventEntity WHERE id = :id")
    suspend fun getEventAndSyncState(id: String): EventAndSyncState?

    @Upsert
    fun upsert(eventEntity: EventEntity)

    @Upsert
    fun upsert(attendees: List<AttendeeEntity>)

    @Transaction
    suspend fun upsertSyncStateAndEvent(eventEntity: EventEntity, attendees: List<AttendeeEntity>) {
        upsert(eventEntity)
        upsert(attendees)
    }

    @Query("DELETE FROM eventEntity WHERE id = :id")
    suspend fun deleteEvent(id: String)
}
