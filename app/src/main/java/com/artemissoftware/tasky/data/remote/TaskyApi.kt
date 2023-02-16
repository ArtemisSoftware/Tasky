package com.artemissoftware.tasky.data.remote

import com.artemissoftware.tasky.data.remote.dto.authentication.LoginBodyDto
import com.artemissoftware.tasky.data.remote.dto.authentication.LoginResponseDto
import com.artemissoftware.tasky.data.remote.dto.authentication.RegistrationBodyDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskyApi {

    @POST("register")
    suspend fun registerUser(@Body registrationBodyDto: RegistrationBodyDto): ResponseBody

    @POST("login")
    suspend fun loginUser(@Body loginBodyDto: LoginBodyDto): LoginResponseDto

    @GET("authenticate")
    suspend fun authenticate(): ResponseBody

    @GET("logout")
    suspend fun logoutUser(): ResponseBody
}