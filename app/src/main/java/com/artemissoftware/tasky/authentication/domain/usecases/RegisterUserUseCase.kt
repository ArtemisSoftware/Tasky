package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterUserUseCase constructor(
    private val authenticationRepository: AuthenticationRepository
){

    suspend operator fun invoke(email: String, password: String, fullName: String): Resource<Boolean> {

        val result = authenticationRepository.registerUser(email = email, password = password, fullName = fullName)

        return when(result){
            is ApiNetworkResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.RegisterError
                Resource.Error(exception)
            }
            is ApiNetworkResponse.Success -> {
                Resource.Success(true)
            }
        }
    }
}