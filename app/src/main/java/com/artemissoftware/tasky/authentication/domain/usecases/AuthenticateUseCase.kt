package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository

class AuthenticateUseCase(
    private val authenticationRepository: AuthenticationRepository
){

    suspend operator fun invoke(): Resource<Unit> {

        val result = authenticationRepository.authenticate()

        return when(result){
            is ApiNetworkResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.UserNotAuthenticated
                Resource.Error(exception)
            }
            is ApiNetworkResponse.Success -> {
                Resource.Success(Unit)
            }
        }
    }
}