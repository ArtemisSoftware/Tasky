package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.data.mappers.toAttendee
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.repositories.AttendeeRepository

class AttendeeRepositoryImpl(
    private val agendaApiSource: AgendaApiSource,
) : AttendeeRepository {

    override suspend fun getAttendee(email: String): DataResponse<Attendee?> {
        return try {
            val result = agendaApiSource.getAttendee(email = email)

            if (result.doesUserExist) {
                DataResponse.Success(data = result.toAttendee())
            } else {
                DataResponse.Success(null)
            }
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
