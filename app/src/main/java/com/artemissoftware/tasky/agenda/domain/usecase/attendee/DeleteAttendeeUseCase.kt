package com.artemissoftware.tasky.agenda.domain.usecase.attendee

import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.repositories.AttendeeRepository
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import javax.inject.Inject

class DeleteAttendeeUseCase @Inject constructor(
    private val attendeeRepository: AttendeeRepository,
    private val eventRepository: EventRepository,
    private val alarmScheduler: AlarmScheduler,
) {

    suspend operator fun invoke(eventId: String): Resource<Unit> {

        eventRepository.deleteEventAndSync(id = eventId)
        alarmScheduler.cancel(eventId)

        val result = attendeeRepository.deleteAttendee(eventId = eventId)


        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AgendaException.AttendeeError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                Resource.Success(Unit)
            }
        }
    }
}
