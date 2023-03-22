package com.artemissoftware.core.domain.repositories

import com.artemissoftware.core.domain.models.authentication.User
import kotlinx.coroutines.flow.Flow

interface UserStoreRepository {

    suspend fun saveUser(user: User)
    fun getUser(): Flow<User>
}
