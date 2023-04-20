package com.artemissoftware.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.mappers.toEvent
import com.artemissoftware.tasky.agenda.data.mappers.toReminder
import com.artemissoftware.tasky.agenda.data.mappers.toTask
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class SyncLocalWithRemoteDataWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val reminderRepository: ReminderRepository,
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val agendaApiSource: AgendaApiSource,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            val today = LocalDateTime.now().toLong()
            val agendaDto = agendaApiSource.getAgenda(today)

            // TODO: should I delete all local data before saving the remote data locally?

            reminderRepository.syncRemindersWithRemote(agendaDto.reminders.map { it.toReminder() })
            taskRepository.syncTasksWithRemote(agendaDto.tasks.map { it.toTask() })
            eventRepository.syncEventsWithRemote(agendaDto.events.map { it.toEvent() })

            Result.success()
        } catch (ex: TaskyNetworkException) {
            Result.retry()
        }
    }
}
