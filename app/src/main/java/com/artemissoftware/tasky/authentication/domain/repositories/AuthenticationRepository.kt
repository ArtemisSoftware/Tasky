package com.artemissoftware.tasky.authentication.domain.repositories

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.models.authentication.User

interface AuthenticationRepository {

    suspend fun registerUser(email: String, password: String, fullName: String): ApiNetworkResponse<Boolean>

    suspend fun loginUser(email: String, password: String): ApiNetworkResponse<User>

    suspend fun authenticate(): ApiNetworkResponse<Boolean>

    suspend fun logoutUser(): ApiNetworkResponse<Boolean>

}