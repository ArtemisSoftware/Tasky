package com.artemissoftware.tasky.authentication.di

import com.artemissoftware.tasky.authentication.data.remote.source.AuthenticationApiSource
import com.artemissoftware.tasky.authentication.data.repositories.AuthenticationRepositoryImpl
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
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
    fun provideAuthenticationRepository(authenticationApiSource: AuthenticationApiSource): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationApiSource = authenticationApiSource)
    }
}
