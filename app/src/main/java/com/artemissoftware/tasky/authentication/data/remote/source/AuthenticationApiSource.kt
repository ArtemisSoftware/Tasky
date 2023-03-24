package com.artemissoftware.tasky.authentication.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.tasky.authentication.data.remote.TaskyAuthenticationApi
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginBodyDto
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import okhttp3.ResponseBody
import javax.inject.Inject

class AuthenticationApiSource @Inject constructor(private val taskyAuthenticationApi: TaskyAuthenticationApi) {

    suspend fun registerUser(registrationBodyDto: RegistrationBodyDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.registerUser(registrationBodyDto)
        }
    }

    suspend fun loginUser(loginBodyDto: LoginBodyDto): LoginResponseDto {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.loginUser(loginBodyDto)
        }
    }

    suspend fun authenticate(): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.authenticate()
        }
    }

    suspend fun logoutUser(): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.logoutUser()
        }
    }
}