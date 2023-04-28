package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
    private val authenticationRepository: AuthenticationRepository,
) {

    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {
        val result = authenticationRepository.loginUser(email = email, password = password)

        return when (result) {
            is DataResponse.Error -> {
                val exception = result.exception?.let {
                    if (it.code == 401) {
                        AuthenticationException.LoginError
                    } else {
                        result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.LoginError
                    }
                } ?: AuthenticationException.LoginError
                Resource.Error(exception)
            }
            is DataResponse.Success -> {
                result.data?.let { userStoreRepository.saveUser(it) }
                Resource.Success(true)
            }
        }
    }
}
