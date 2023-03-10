package com.artemissoftware.tasky.authentication.domain.usecases

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

    operator fun invoke(email: String, password: String, fullName: String): Flow<Resource<Boolean>> = flow {

        emit(Resource.Loading())

        val result = authenticationRepository.registerUser(email = email, password = password, fullName = fullName)

        when(result){
            is ApiNetworkResponse.Error -> {
                val error = result.exception?.description ?: UiText.DynamicString(REGISTER_ERROR)
                emit(Resource.Error(error))
            }
            is ApiNetworkResponse.Success -> {
                emit(Resource.Success(true))
            }
        }
    }


    companion object{
        private const val REGISTER_ERROR = "An error occurred while doing registering"
    }
}