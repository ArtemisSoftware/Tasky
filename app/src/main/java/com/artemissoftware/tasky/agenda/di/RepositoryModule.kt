package com.artemissoftware.tasky.agenda.di

import android.content.Context
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.tasky.agenda.data.alarm.AlarmSchedulerImpl
import com.artemissoftware.tasky.agenda.data.compressor.ImageCompressorImpl
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.data.repositories.AgendaRepositoryImpl
import com.artemissoftware.tasky.agenda.data.repositories.AttendeeRepositoryImpl
import com.artemissoftware.tasky.agenda.data.repositories.ReminderRepositoryImpl
import com.artemissoftware.tasky.agenda.data.repositories.TaskRepositoryImpl
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.compressor.ImageCompressor
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.agenda.domain.repositories.AttendeeRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAgendaRepository(agendaApiSource: AgendaApiSource, database: TaskyDatabase): AgendaRepository {
        return AgendaRepositoryImpl(agendaApiSource = agendaApiSource, taskDao = database.taskDao, reminderDao = database.reminderDao)
    }

    @Provides
    @Singleton
    fun provideAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AlarmSchedulerImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideAttendeeRepository(agendaApiSource: AgendaApiSource): AttendeeRepository {
        return AttendeeRepositoryImpl(agendaApiSource = agendaApiSource)
    }

    @Provides
    @Singleton
    fun provideImageCompressor(@ApplicationContext context: Context): ImageCompressor {
        return ImageCompressorImpl(context = context)
    }
}
