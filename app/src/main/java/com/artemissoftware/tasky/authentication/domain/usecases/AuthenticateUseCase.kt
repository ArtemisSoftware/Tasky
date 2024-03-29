package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(): Resource<Unit> {
        val result = authenticationRepository.authenticate()

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.UserNotAuthenticated
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                Resource.Success(Unit)
            }
        }
    }
}
