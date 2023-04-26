package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.Attendee

interface AttendeeRepository {

    suspend fun getAttendee(email: String): DataResponse<Attendee?>

    suspend fun deleteAttendee(eventId: String): DataResponse<Unit>

    suspend fun deleteSyncState(eventId: String)
}
