package com.artemissoftware.tasky.authentication.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.tasky.authentication.data.remote.TaskyAuthenticationApi
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginBodyDto
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import okhttp3.ResponseBody

class TaskyAuthenticationApiSourceImpl constructor(private val taskyAuthenticationApi: TaskyAuthenticationApi) : TaskyAuthenticationApiSource {

    override suspend fun registerUser(registrationBodyDto: RegistrationBodyDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.registerUser(registrationBodyDto)
        }
    }

    override suspend fun loginUser(loginBodyDto: LoginBodyDto): LoginResponseDto {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.loginUser(loginBodyDto)
        }
    }

    override suspend fun authenticate(): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.authenticate()
        }
    }

    override suspend fun logoutUser(): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.logoutUser()
        }
    }
}