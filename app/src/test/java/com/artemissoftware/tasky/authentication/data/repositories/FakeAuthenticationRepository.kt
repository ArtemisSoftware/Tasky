package com.artemissoftware.tasky.authentication.data.repositories

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.util.FakeData

class FakeAuthenticationRepository : AuthenticationRepository {

    var returnNetworkError = false

    override suspend fun registerUser(
        email: String,
        password: String,
        fullName: String
    ): ApiNetworkResponse<Boolean> {
        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(true)
        }
    }

    override suspend fun loginUser(email: String, password: String): ApiNetworkResponse<User> {
        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(FakeData.user)
        }
    }

    override suspend fun authenticate(): ApiNetworkResponse<Boolean> {
        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(true)
        }
    }

    override suspend fun logoutUser(): ApiNetworkResponse<Boolean> {
        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(true)
        }
    }
}