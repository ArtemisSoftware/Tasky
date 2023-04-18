package com.artemissoftware.tasky.authentication.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.authentication.User

interface AuthenticationRepository {

    suspend fun registerUser(email: String, password: String, fullName: String): DataResponse<Boolean>

    suspend fun loginUser(email: String, password: String): DataResponse<User>

    suspend fun authenticate(): DataResponse<Boolean>
}
