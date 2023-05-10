package com.artemissoftware.core.data.repositories

import android.content.Context
import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.core.domain.repositories.UserRepository
import com.artemissoftware.core.util.extensions.userStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val context: Context,
    private val reminderDao: ReminderDao,
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
) : UserRepository {

    override suspend fun saveUser(user: User) {
        context.userStore.updateData {
            User(
                fullName = user.fullName,
                token = user.token,
                id = user.id,
            )
        }
    }

    override fun getUser(): Flow<User> {
        return context.userStore.data
    }

    override suspend fun deleteUser() {
        context.userStore.updateData {
            User()
        }
    }

    override suspend fun deleteAllUserData(): List<String> {
        val deletedRemindersId = reminderDao.deleteAll()
        val deletedTasksId = taskDao.deleteAll()
        val deletedEventsId = eventDao.deleteAll()
        return (deletedRemindersId + deletedTasksId + deletedEventsId)
    }
}
