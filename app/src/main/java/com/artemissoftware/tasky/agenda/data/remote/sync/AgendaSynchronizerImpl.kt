package com.artemissoftware.tasky.agenda.data.remote.sync

import androidx.work.*
import com.artemissoftware.core.util.extensions.toEpochMilli
import com.artemissoftware.tasky.agenda.data.remote.worker.AgendaSyncWorker
import com.artemissoftware.tasky.agenda.data.remote.worker.SyncLocalWithRemoteDataWorker
import com.artemissoftware.tasky.agenda.data.remote.worker.SyncRemoteWithLocalDataWorker
import com.artemissoftware.tasky.agenda.domain.sync.AgendaSynchronizer
import com.artemissoftware.tasky.agenda.util.WorkerKeys
import java.time.Duration
import java.time.LocalDate
import java.util.UUID
import java.util.concurrent.TimeUnit

class AgendaSynchronizerImpl(
    private val workManager: WorkManager,
) : AgendaSynchronizer {

    override fun syncAgenda(currentDate: LocalDate): UUID {
        workManager.cancelAllWorkByTag("syncAgenda")

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
            .setInputData(
                Data.Builder()
                    .putLong(WorkerKeys.SELECTED_DATE, currentDate.toEpochMilli())
                    .build(),
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .addTag("syncAgenda")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "sync_agenda",
            ExistingPeriodicWorkPolicy.REPLACE,
            syncWorker,
        )

        return syncWorker.id
    }

    override fun syncLocalWithRemoteData(): UUID {
        val syncWorker = PeriodicWorkRequestBuilder<SyncLocalWithRemoteDataWorker>(
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
            .build()

        workManager.enqueueUniquePeriodicWork(
            "sync_local_with_remote",
            ExistingPeriodicWorkPolicy.REPLACE,
            syncWorker,
        )

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
