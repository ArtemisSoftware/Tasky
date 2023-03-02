package com.artemissoftware.tasky.authentication.data.remote.source

import com.artemissoftware.tasky.authentication.data.remote.dto.LoginBodyDto
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import okhttp3.ResponseBody

interface TaskyAuthenticationApiSource {

    suspend fun registerUser(registrationBodyDto: RegistrationBodyDto): ResponseBody
    suspend fun loginUser(loginBodyDto: LoginBodyDto): LoginResponseDto
    suspend fun authenticate(): ResponseBody
    suspend fun logoutUser(): ResponseBody
}