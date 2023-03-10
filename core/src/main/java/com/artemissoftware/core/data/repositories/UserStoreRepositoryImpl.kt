package com.artemissoftware.core.data.repositories

import android.content.Context
import com.artemissoftware.core.util.extensions.userStore
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import kotlinx.coroutines.flow.Flow

class UserStoreRepositoryImpl(private val context: Context) : UserStoreRepository {

    override suspend fun saveUser(user: User) {
        context.userStore.updateData {
            it.copy(
                fullName = user.fullName,
                token = user.token,
                id = user.id,
            )
        }
    }

    override fun getUser(): Flow<User> {
        return context.userStore.data
    }
}