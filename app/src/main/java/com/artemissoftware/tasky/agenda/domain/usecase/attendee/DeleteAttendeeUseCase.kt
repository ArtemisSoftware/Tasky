package com.artemissoftware.tasky.agenda.domain.usecase.attendee

import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.agenda.domain.repositories.AttendeeRepository
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import javax.inject.Inject

class DeleteAttendeeUseCase @Inject constructor(
    private val attendeeRepository: AttendeeRepository,
    private val eventRepository: EventRepository,
) {

    suspend operator fun invoke(eventId: String): Resource<Unit> {
        val result = attendeeRepository.deleteAttendee(eventId = eventId)

        // TODO: localy delete the event. This method is in another PR
        // eventRepository.deleteEvent(eventId = eventId)

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AgendaException.AttendeeError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                attendeeRepository.deleteSyncState(eventId = eventId)
                Resource.Success(Unit)
            }
        }
    }
}
