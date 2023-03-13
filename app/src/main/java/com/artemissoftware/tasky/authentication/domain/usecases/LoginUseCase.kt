package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.core.util.UiText
import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository

class LoginUseCase constructor(
    private val userStoreRepository: UserStoreRepository,
    private val authenticationRepository: AuthenticationRepository
){

    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {

        val result = authenticationRepository.loginUser(email = email, password = password)

        return when(result){
            is ApiNetworkResponse.Error -> {
                val exception = result.exception?.description?.let { ValidationException.DataError(it) } ?: AuthenticationException.LoginError
                Resource.Error(exception)
            }
            is ApiNetworkResponse.Success -> {
                result.data?.let { userStoreRepository.saveUser(it) }
                Resource.Success(true)
            }
        }
    }
}