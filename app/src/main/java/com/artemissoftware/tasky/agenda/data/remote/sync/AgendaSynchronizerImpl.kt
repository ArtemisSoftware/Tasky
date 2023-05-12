package com.artemissoftware.tasky.agenda.data.remote.sync

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.artemissoftware.tasky.agenda.data.remote.worker.AgendaSyncWorker
import com.artemissoftware.tasky.agenda.data.remote.worker.SyncLocalWithRemoteDataWorker
import com.artemissoftware.tasky.agenda.data.remote.worker.SyncRemoteWithLocalDataWorker
import com.artemissoftware.tasky.agenda.domain.sync.AgendaSynchronizer
import java.time.Duration
import java.util.UUID
import java.util.concurrent.TimeUnit

class AgendaSynchronizerImpl(
    private val workManager: WorkManager,
) : AgendaSynchronizer {

    override fun syncAgenda(): UUID {
        val syncWorker: PeriodicWorkRequest = PeriodicWorkRequestBuilder<AgendaSyncWorker>(
            repeatInterval = 15,
            TimeUnit.MINUTES,
            flexTimeInterval = 5,
            TimeUnit.MINUTES,
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .addTag("syncAgenda")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "sync_agenda",
            ExistingPeriodicWorkPolicy.UPDATE,
            syncWorker,
        )

        return syncWorker.id
    }

    override fun syncLocalWithRemoteData(): UUID {
        val syncWorker = OneTimeWorkRequestBuilder<SyncLocalWithRemoteDataWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()
        workManager.beginUniqueWork(
            "sync_local_with_remote",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            syncWorker,
        ).enqueue()

        return syncWorker.id
    }

    override fun syncRemoteWithLocalData(): UUID {
        val syncWorker = OneTimeWorkRequestBuilder<SyncRemoteWithLocalDataWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()
        workManager.beginUniqueWork(
            "sync_remote_with_local",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            syncWorker,
        ).enqueue()

        return syncWorker.id
    }
}
