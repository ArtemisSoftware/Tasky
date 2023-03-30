package com.artemissoftware.tasky.agenda.di

import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.data.repositories.ReminderRepositoryImpl
import com.artemissoftware.tasky.agenda.data.repositories.TaskRepositoryImpl
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideReminderRepository(agendaApiSource: AgendaApiSource, database: TaskyDatabase): ReminderRepository {
        return ReminderRepositoryImpl(agendaApiSource = agendaApiSource, reminderDao = database.reminderDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(agendaApiSource: AgendaApiSource, database: TaskyDatabase): TaskRepository {
        return TaskRepositoryImpl(agendaApiSource = agendaApiSource, taskDao = database.taskDao)
    }
}
