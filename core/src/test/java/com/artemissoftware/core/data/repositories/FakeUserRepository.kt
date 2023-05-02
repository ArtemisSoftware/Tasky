package com.artemissoftware.core.data.repositories

import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.core.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository: UserRepository {

    private lateinit var user: User

    override suspend fun saveUser(user: User) {
        this.user = user
    }

    override fun getUser(): Flow<User> = flow{
        user
    }
}