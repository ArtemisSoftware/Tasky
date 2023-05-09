package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.repositories.UserRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {
        val result = authenticationRepository.loginUser(email = email, password = password)

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.LoginError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                result.data?.let { userRepository.saveUser(it) }
                Resource.Success(true)
            }
        }
    }
}
