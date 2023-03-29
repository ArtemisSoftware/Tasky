package com.artemissoftware.tasky.authentication.di

import com.artemissoftware.tasky.authentication.data.remote.TaskyAuthenticationApi
import com.artemissoftware.tasky.authentication.data.remote.source.AuthenticationApiSource
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
    fun provideTaskyAuthenticationApi(retrofit: Retrofit): TaskyAuthenticationApi {
        return retrofit
            .create(TaskyAuthenticationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationApiSource(taskyAuthenticationApi: TaskyAuthenticationApi): AuthenticationApiSource {
        return AuthenticationApiSource(taskyAuthenticationApi)
    }
}
