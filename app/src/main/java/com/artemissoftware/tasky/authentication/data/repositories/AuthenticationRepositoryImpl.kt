package com.artemissoftware.tasky.authentication.data.repositories

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.authentication.data.mappers.toUser
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginBodyDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import com.artemissoftware.tasky.authentication.data.remote.source.AuthenticationApiSource
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl constructor(
    private val authenticationApiSource: AuthenticationApiSource,
) : AuthenticationRepository {

    override suspend fun registerUser(email: String, password: String, fullName: String): DataResponse<Boolean> {
        val registrationBodyDto = RegistrationBodyDto(email = email, password = password, fullName = fullName)

        return try {
            authenticationApiSource.registerUser(registrationBodyDto)
            DataResponse.Success(data = true)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun loginUser(email: String, password: String): DataResponse<User> {
        val loginBodyDto = LoginBodyDto(email = email, password = password)

        return try {
            val result = authenticationApiSource.loginUser(loginBodyDto)
            DataResponse.Success(data = result.toUser())
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun authenticate(): DataResponse<Boolean> {
        return try {
            authenticationApiSource.authenticate()
            DataResponse.Success(data = true)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun logoutUser(): DataResponse<Boolean> {
        return try {
            authenticationApiSource.logoutUser()
            DataResponse.Success(data = true)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
