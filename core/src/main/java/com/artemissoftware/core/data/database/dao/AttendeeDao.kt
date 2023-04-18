package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.AttendeeEntity

@Dao
interface AttendeeDao {

    @Upsert
    fun upsert(attendees: List<AttendeeEntity>)

    @Query("DELETE FROM attendeeEntity WHERE eventId = :eventId")
    suspend fun deleteAttendees(eventId: String)

    @Transaction
    suspend fun upsertAttendees(eventId: String, attendees: List<AttendeeEntity>) {
        deleteAttendees(eventId = eventId)
        upsert(attendees)
    }
}
