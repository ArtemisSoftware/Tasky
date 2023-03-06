package com.artemissoftware.tasky.authentication.data.repositories

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.authentication.data.mappers.toUser
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginBodyDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import com.artemissoftware.tasky.authentication.data.remote.source.AuthenticationApiSource
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl constructor(
    private val authenticationApiSource: AuthenticationApiSource
): AuthenticationRepository {


    override suspend fun registerUser(email: String, password: String, fullName: String): ApiNetworkResponse<Boolean> {

        val registrationBodyDto = RegistrationBodyDto(email = email, password = password, fullName = fullName)

        return try {

            authenticationApiSource.registerUser(registrationBodyDto)
            ApiNetworkResponse.Success(data = true)

        } catch (ex: TaskyNetworkException) {
            ApiNetworkResponse.Error(exception = ex)
        }
    }

    override suspend fun loginUser(email: String, password: String): ApiNetworkResponse<User> {

        val loginBodyDto = LoginBodyDto(email = email, password = password)

        return try {

            val result = authenticationApiSource.loginUser(loginBodyDto)
            ApiNetworkResponse.Success(data = result.toUser())

        } catch (ex: TaskyNetworkException) {
            ApiNetworkResponse.Error(exception = ex)
        }
    }

    override suspend fun authenticate(): ApiNetworkResponse<Boolean> {

        return try {

            authenticationApiSource.authenticate()
            ApiNetworkResponse.Success(data = true)

        } catch (ex: TaskyNetworkException) {
            ApiNetworkResponse.Error(exception = ex)
        }
    }

    override suspend fun logoutUser(): ApiNetworkResponse<Boolean> {

        return try {

            authenticationApiSource.logoutUser()
            ApiNetworkResponse.Success(data = true)

        } catch (ex: TaskyNetworkException) {
            ApiNetworkResponse.Error(exception = ex)
        }
    }
}