package com.artemissoftware.core.di

import android.content.Context
import androidx.room.Room
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        TaskyDatabase::class.java,
        Constants.TASKY_DATABASE,
    ).build()
}
