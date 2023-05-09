package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.repositories.UserRepository
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val agendaRepository: AgendaRepository,
) {

    suspend operator fun invoke(): Resource<Unit> {
        val result = agendaRepository.logOut()

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AgendaException.LogOutError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                agendaRepository.deleteLocalAgenda()
                userRepository.deleteUser()
                Resource.Success(Unit)
            }
        }
    }
}
