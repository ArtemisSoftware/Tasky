package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase constructor(
    private val userStoreRepository: UserStoreRepository,
    private val authenticationRepository: AuthenticationRepository
){

    suspend operator fun invoke(email: String, password: String): Resource<Boolean> {

        val result = authenticationRepository.loginUser(email = email, password = password)

        return when(result){
            is ApiNetworkResponse.Error -> {
                val error = result.exception?.description ?: UiText.DynamicString(LOGIN_ERROR)
                Resource.Error(error)
            }
            is ApiNetworkResponse.Success -> {
                result.data?.let { userStoreRepository.saveUser(it) }
                Resource.Success(true)
            }
        }
    }


    companion object{
        private const val LOGIN_ERROR = "An error occurred while doing login in"
    }
}