package com.artemissoftware.core.data.remote.api

import com.artemissoftware.core.data.remote.dto.authentication.LoginBodyDto
import com.artemissoftware.core.data.remote.dto.authentication.LoginResponseDto
import com.artemissoftware.core.data.remote.dto.authentication.RegistrationBodyDto
import com.artemissoftware.core.util.annotations.NoJWTHeaderRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyAuthenticationApi {

    @NoJWTHeaderRequest
    @POST("register")
    suspend fun registerUser(@Body registrationBodyDto: RegistrationBodyDto): ResponseBody

    @NoJWTHeaderRequest
    @POST("login")
    suspend fun loginUser(@Body loginBodyDto: LoginBodyDto): LoginResponseDto

    @GET("authenticate")
    suspend fun authenticate(): ResponseBody

    @GET("logout")
    suspend fun logoutUser(): ResponseBody
}
