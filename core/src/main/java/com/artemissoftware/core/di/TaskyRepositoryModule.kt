package com.artemissoftware.core.di

import android.content.Context
import com.artemissoftware.core.data.repositories.UserStoreRepositoryImpl
import com.artemissoftware.core.domain.repositories.UserStoreRepository
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
    fun provideUserStoreRepository(@ApplicationContext context: Context): UserStoreRepository {
        return UserStoreRepositoryImpl(context = context)
    }
}