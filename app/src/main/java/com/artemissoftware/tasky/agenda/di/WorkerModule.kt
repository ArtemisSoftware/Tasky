package com.artemissoftware.tasky.agenda.di

import android.content.Context
import androidx.work.WorkManager
import com.artemissoftware.tasky.agenda.data.remote.sync.SyncAgendaImpl
import com.artemissoftware.tasky.agenda.data.remote.upload.EventUploaderImpl
import com.artemissoftware.tasky.agenda.domain.sync.SyncAgenda
import com.artemissoftware.tasky.agenda.domain.uploader.EventUploader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideEventUploader(workManager: WorkManager): EventUploader {
        return EventUploaderImpl(workManager = workManager)
    }

    @Provides
    @Singleton
    fun provideSyncAgenda(workManager: WorkManager): SyncAgenda {
        return SyncAgendaImpl(workManager = workManager)
    }
}
