package com.artemissoftware.tasky.authentication.data.repositories

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.util.FakeData

class FakeAuthenticationRepository : AuthenticationRepository {

    var returnNetworkError = false
    var errorWithBackendMessage = false

    override suspend fun registerUser(
        email: String,
        password: String,
        fullName: String
    ): DataResponse<Boolean> {
        return if(returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else if(errorWithBackendMessage){
            DataResponse.Error(TaskyNetworkException(code = 0, description = BACKEND_ERROR))
        } else {
            DataResponse.Success(true)
        }
    }

    override suspend fun loginUser(email: String, password: String): DataResponse<User> {
        return if(returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else if(errorWithBackendMessage){
            DataResponse.Error(TaskyNetworkException(code = 0, description = BACKEND_ERROR))
        }
        else {
            DataResponse.Success(FakeData.user)
        }
    }

    override suspend fun authenticate(): DataResponse<Boolean> {
        return if(returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else if(errorWithBackendMessage){
            DataResponse.Error(TaskyNetworkException(code = 0, description = BACKEND_ERROR))
        } else {
            DataResponse.Success(true)
        }
    }

    override suspend fun logoutUser(): DataResponse<Boolean> {
        return if(returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else {
            DataResponse.Success(true)
        }
    }

    companion object{
        const val BACKEND_ERROR = "Backend error"
    }
}