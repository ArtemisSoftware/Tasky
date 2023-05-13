package com.artemissoftware.tasky.agenda.domain.usecase.attendee

import com.artemissoftware.core.data.mappers.toResource
import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.repositories.AttendeeRepository
import javax.inject.Inject

class GetAttendeeUseCase @Inject constructor(
    private val attendeeRepository: AttendeeRepository,
) {

    suspend operator fun invoke(email: String): Resource<Attendee> {
        val result = attendeeRepository.getAttendee(email = email)

        return when (result) {
            is DataResponse.Error -> {

                var resource = result.exception.toResource<Attendee>(defaultException = AgendaException.AttendeeError)

                result.exception?.let {
                    if (it.code == 409) {
                        resource = Resource.Error(AgendaException.AttendeeCannotAddItself(it.description))
                    }
                }

                resource
            }
            is DataResponse.Success -> {
                result.data?.let { Resource.Success(it) } ?: Resource.Error(AgendaException.AttendeeDoesNotExist)
            }
        }
    }
}
