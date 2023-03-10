package com.artemissoftware.tasky.authentication.data.remote

import com.artemissoftware.tasky.authentication.data.remote.dto.LoginBodyDto
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyAuthenticationApi {

    @POST("register")
    suspend fun registerUser(@Body registrationBodyDto: RegistrationBodyDto): ResponseBody

    @POST("login")
    suspend fun loginUser(@Body loginBodyDto: LoginBodyDto): LoginResponseDto

    @GET("authenticate")
    suspend fun authenticate(): ResponseBody

    @GET("logout")
    suspend fun logoutUser(): ResponseBody
}