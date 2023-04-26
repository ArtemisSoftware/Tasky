package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.core.data.database.entities.AttendeeSyncEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.domain.SyncType

@Dao
interface AttendeeDao {

    @Upsert
    fun upsert(attendees: List<AttendeeEntity>)

    @Query("DELETE FROM attendeeEntity WHERE eventId = :eventId")
    suspend fun deleteAttendees(eventId: String)

    @Upsert
    suspend fun upsertSyncState(attendeeSyncEntity: AttendeeSyncEntity)

    @Query("DELETE FROM attendeeSyncEntity WHERE id = :id")
    suspend fun deleteSyncState(id: String)

    @Transaction
    suspend fun upsertAttendees(eventId: String, attendees: List<AttendeeEntity>) {
        deleteAttendees(eventId = eventId)
        upsert(attendees)
    }

    @Query("SELECT * FROM attendeeSyncEntity WHERE syncType IN (:types)")
    suspend fun getAttendeesToSync(types: Array<SyncType> = arrayOf(SyncType.CREATE, SyncType.UPDATE, SyncType.DELETE)): List<TaskSyncEntity>
}
