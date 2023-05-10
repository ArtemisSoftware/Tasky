package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, password: String, fullName: String): Resource<Unit> {
        val result = authenticationRepository.registerUser(email = email, password = password, fullName = fullName)

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.RegisterError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                Resource.Success(Unit)
            }
        }
    }
}
