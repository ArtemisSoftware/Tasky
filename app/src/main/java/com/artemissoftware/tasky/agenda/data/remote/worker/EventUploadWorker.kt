package com.artemissoftware.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.UiText
import com.artemissoftware.core.util.safeLet
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.compressor.ImageCompressor
import com.artemissoftware.tasky.agenda.util.WorkerKeys.ERROR_MSG
import com.artemissoftware.tasky.agenda.util.WorkerKeys.EVENT_JSON
import com.artemissoftware.tasky.agenda.util.WorkerKeys.EVENT_PICTURE_LIST
import com.artemissoftware.tasky.agenda.util.WorkerKeys.SYNC_TYPE
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

@HiltWorker
class EventUploaderWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val imageCompressor: ImageCompressor,
    private val agendaApiSource: AgendaApiSource,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        with(workerParameters.inputData) {
            safeLet(getString(EVENT_JSON), SyncType.getSyncTypeByName(getString(SYNC_TYPE).orEmpty())) { eventJson, syncType ->

                val pictures = getStringArray(EVENT_PICTURE_LIST) ?: emptyArray()
                val picturesMultipart = pictureToMultipart(pictures.toList())

                return try {
                    syncEvent(eventJson = eventJson, pictures = picturesMultipart, syncType = syncType)
                    Result.success()
                } catch (ex: TaskyNetworkException) {
                    Result.failure(
                        workDataOf(ERROR_MSG to ex.description),
                    )
                }
            } ?: return Result.failure(
                workDataOf(ERROR_MSG to UiText.DynamicString("No data to upload or a synchronization type")),
            )
        }
    }

    private suspend fun pictureToMultipart(photos: List<String>): List<MultipartBody.Part> {
        return photos.mapIndexed { index, pictureUri ->
            val imageCompressed = imageCompressor.compressUri(pictureUri)

            MultipartBody.Part.createFormData(
                "photo$index",
                filename = UUID.randomUUID().toString(),
                imageCompressed.toRequestBody(),
            )
        }
    }

    private suspend fun syncEvent(
        eventJson: String,
        pictures: List<MultipartBody.Part>,
        syncType: SyncType,
    ) {
        when (syncType) {
            SyncType.CREATE -> {
                agendaApiSource.createEvent(
                    eventBody = MultipartBody.Part.createFormData("create_event_request", eventJson),
                    pictures = pictures,
                )
            }
            SyncType.UPDATE -> {
                agendaApiSource.updateEvent(
                    eventBody = MultipartBody.Part.createFormData("update_event_request", eventJson),
                    pictures = pictures,
                )
            }
            else -> Unit
        }
    }
}
