package com.artemissoftware.core.domain.repositories

import com.artemissoftware.core.domain.models.authentication.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUser(user: User)
    fun getUser(): Flow<User>

    suspend fun deleteUser()

    suspend fun deleteAllUserData(): List<String>
}
