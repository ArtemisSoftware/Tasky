package com.artemissoftware.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import com.artemissoftware.tasky.agenda.domain.uploader.EventUploader
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltWorker
class SyncRemoteWithLocalData @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val reminderRepository: ReminderRepository,
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val eventUploader: EventUploader,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            supervisorScope {
                val reminderJobs = remindersSync(coroutineScope = this)
                val taskJobs = tasksSync(coroutineScope = this)
                val eventJobs = eventsSync(coroutineScope = this)
                (reminderJobs + taskJobs + eventJobs).forEach { it.join() }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun remindersSync(coroutineScope: CoroutineScope): List<Job> = with(reminderRepository) {
        val jobs = getRemindersToSync().map { syncState ->

            coroutineScope.launch {
                when (syncState.syncType) {
                    SyncType.CREATE, SyncType.UPDATE -> {
                        getReminder(id = syncState.itemId)?.let { reminder ->
                            saveReminderAndSync(reminder)
                        }
                    }
                    SyncType.DELETE -> {
                        deleteReminderAndSync(syncState.itemId)
                    }
                    else -> Unit
                }
            }
        }

        return jobs
    }

    private suspend fun tasksSync(coroutineScope: CoroutineScope): List<Job> = with(taskRepository) {
        val jobs = getTasksToSync().map { syncState ->

            coroutineScope.launch {
                when (syncState.syncType) {
                    SyncType.CREATE, SyncType.UPDATE -> {
                        getTask(id = syncState.itemId)?.let { task ->
                            saveTaskAndSync(task)
                        }
                    }
                    SyncType.DELETE -> {
                        taskRepository.deleteTaskAndSync(syncState.itemId)
                    }
                    else -> Unit
                }
            }
        }

        return jobs
    }

    private suspend fun eventsSync(coroutineScope: CoroutineScope): List<Job> = with(eventRepository) {
        val jobs = getEventsToSync().map { syncState ->

            coroutineScope.launch {
                when (syncState.syncType) {
                    SyncType.CREATE, SyncType.UPDATE -> {
                        getEvent(id = syncState.itemId)?.let { event ->
                            eventUploader.upload(event = event, syncType = syncState.syncType)
                        }
                    }
                    SyncType.DELETE -> {
                        eventRepository.deleteEventAndSync(syncState.itemId)
                    }
                    else -> Unit
                }
            }
        }

        return jobs
    }
}
