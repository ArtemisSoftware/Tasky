package com.artemissoftware.tasky.authentication.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.core.data.remote.api.TaskyAuthenticationApi
import com.artemissoftware.core.data.remote.dto.authentication.LoginBodyDto
import com.artemissoftware.core.data.remote.dto.authentication.LoginResponseDto
import com.artemissoftware.core.data.remote.dto.authentication.RegistrationBodyDto
import okhttp3.ResponseBody

class AuthenticationApiSource constructor(private val taskyAuthenticationApi: TaskyAuthenticationApi) {

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
}
