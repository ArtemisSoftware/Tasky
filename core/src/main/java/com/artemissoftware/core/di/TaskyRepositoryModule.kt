package com.artemissoftware.core.di

import android.content.Context
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.core.data.repositories.UserRepositoryImpl
import com.artemissoftware.core.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskyRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context, database: TaskyDatabase): UserRepository {
        return UserRepositoryImpl(context = context, reminderDao = database.reminderDao, taskDao = database.taskDao, eventDao = database.eventDao)
    }
}
