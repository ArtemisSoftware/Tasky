package com.artemissoftware.tasky.authentication.di

import com.artemissoftware.core.data.remote.api.TaskyAuthenticationApi
import com.artemissoftware.tasky.authentication.data.remote.source.AuthenticationApiSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthenticationApiSource(taskyAuthenticationApi: TaskyAuthenticationApi): AuthenticationApiSource {
        return AuthenticationApiSource(taskyAuthenticationApi)
    }
}
