package com.artemissoftware.tasky.agenda.di

import com.artemissoftware.tasky.agenda.data.remote.TaskyAgendaApi
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTaskyAgendaApi(retrofit: Retrofit): TaskyAgendaApi {
        return retrofit
            .create(TaskyAgendaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAgendaApiSource(taskyAgendaApi: TaskyAgendaApi): AgendaApiSource {
        return AgendaApiSource(taskyAgendaApi)
    }
}