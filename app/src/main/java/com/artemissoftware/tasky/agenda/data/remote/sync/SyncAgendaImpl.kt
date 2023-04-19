package com.artemissoftware.tasky.agenda.data.remote.sync

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.artemissoftware.tasky.agenda.data.remote.worker.SyncLocalWithRemoteDataWorker
import com.artemissoftware.tasky.agenda.domain.sync.SyncAgenda
import java.time.Duration
import java.util.concurrent.TimeUnit

class SyncAgendaImpl(
    private val workManager: WorkManager,
) : SyncAgenda {

    override fun syncLocalWithRemoteData() {
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
            SYNC_LOCAL_WITH_REMOTE,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorker,
        )
    }

    companion object{
        private const val SYNC_LOCAL_WITH_REMOTE = "sync_local_with_remote"
    }
}
