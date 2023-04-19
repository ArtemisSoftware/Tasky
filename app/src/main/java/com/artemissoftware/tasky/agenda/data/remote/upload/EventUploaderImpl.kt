package com.artemissoftware.tasky.agenda.data.remote.upload

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.tasky.agenda.data.mappers.toCreateBodyDto
import com.artemissoftware.tasky.agenda.data.mappers.toUpdateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventCreateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventUpdateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.worker.EventUploaderWorker
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.Picture
import com.artemissoftware.tasky.agenda.domain.uploader.EventUploader
import com.artemissoftware.tasky.agenda.util.WorkerKeys
import com.squareup.moshi.Moshi
import java.time.Duration

class EventUploaderImpl(
    private val workManager: WorkManager,
) : EventUploader {

    override suspend fun upload(event: AgendaItem.Event, syncType: SyncType) {
        val eventJson = eventToJson(event = event, syncType = syncType)
        val picturesUri = event.pictures.filterIsInstance<Picture.Local>().map { it.uri }

        eventJson?.let {
            val uploaderWorker = OneTimeWorkRequestBuilder<EventUploaderWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build(),
                )
                .setInputData(
                    Data.Builder()
                        .putString(WorkerKeys.EVENT_JSON, eventJson)
                        .putString(WorkerKeys.SYNC_TYPE, syncType.name)
                        .putStringArray(
                            WorkerKeys.EVENT_PICTURE_LIST,
                            picturesUri.toTypedArray(),
                        )
                        .build(),
                )
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(1))
                .build()

            workManager.beginUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.APPEND_OR_REPLACE,
                uploaderWorker,
            ).enqueue()
        }
    }

    private fun eventToJson(event: AgendaItem.Event, syncType: SyncType): String? {
        val moshi = Moshi.Builder().build()

        return when (syncType) {
            SyncType.CREATE -> {
                val jsonAdapter = moshi.adapter(EventCreateBodyDto::class.java)
                jsonAdapter.toJson(event.toCreateBodyDto())
            }
            SyncType.UPDATE -> {
                val jsonAdapter = moshi.adapter(EventUpdateBodyDto::class.java)
                jsonAdapter.toJson(event.toUpdateBodyDto())
            }
            else -> {
                null
            }
        }
    }

    companion object {
        private const val WORK_NAME = "event_upload"
    }
}
