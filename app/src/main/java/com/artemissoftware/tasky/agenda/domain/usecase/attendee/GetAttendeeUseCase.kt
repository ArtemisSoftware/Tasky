package com.artemissoftware.tasky.agenda.domain.usecase.attendee

import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.domain.repositories.AttendeeRepository
import javax.inject.Inject

class GetAttendeeUseCase @Inject constructor(
    private val attendeeRepository: AttendeeRepository,
){

    suspend operator fun invoke(email: String): Resource<Attendee> {
        val result = attendeeRepository.getAttendee(email = email)

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AgendaException.AttendeeError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                result.data?.let { Resource.Success(it) } ?: Resource.Error(AgendaException.AttendeeDoesNotExist)
            }
        }
    }
}
