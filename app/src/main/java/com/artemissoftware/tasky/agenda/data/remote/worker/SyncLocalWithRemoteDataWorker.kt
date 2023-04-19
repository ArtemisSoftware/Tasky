package com.artemissoftware.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.core.data.database.dao.AttendeeDao
import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.dao.PictureDao
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.UiText
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.data.mappers.toEvent
import com.artemissoftware.tasky.agenda.data.mappers.toReminder
import com.artemissoftware.tasky.agenda.data.mappers.toTask
import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.util.WorkerKeys
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class SyncLocalWithRemoteDataWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val agendaApiSource: AgendaApiSource,
    private val taskyDatabase: TaskyDatabase,
    private val reminderDao: ReminderDao,
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val pictureDao: PictureDao,
    private val attendeeDao: AttendeeDao,
    private val alarmScheduler: AlarmScheduler,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return workerParameters.inputData.getString(WorkerKeys.LOGGED_IN_USER_ID)?.let { loggedInUserId ->

            try {
                val today = LocalDateTime.now().toLong()
                val agendaDto = agendaApiSource.getAgenda(today)

                saveReminders(agendaDto = agendaDto)
                saveTasks(agendaDto = agendaDto)
                saveEvents(agendaDto = agendaDto, loggedInUserId = loggedInUserId)

                Result.success()
            } catch (ex: TaskyNetworkException) {
                Result.retry()
            }
        } ?: run {
            Result.failure(
                workDataOf(WorkerKeys.ERROR_MSG to UiText.DynamicString(ERROR_DOWNLOAD)),
            )
        }
    }

    private suspend fun saveEvents(agendaDto: AgendaResponseDto, loggedInUserId: String) {
        if (agendaDto.events.isNotEmpty()) {
            val events = agendaDto.events.map { it.toEntity(loggedInUserId = loggedInUserId) }
            val eventsSyncType = events.map { EventSyncEntity(id = it.id, syncType = SyncType.SYNCED) }
            val pictures = agendaDto.events.flatMap { event -> event.photos.map { it.toEntity(eventId = event.id) } }

            taskyDatabase.withTransaction {
                eventDao.upsertSyncStateAndEvents(events = events, eventsSyncType = eventsSyncType)
                pictureDao.upsert(pictures = pictures)
                val attendees = agendaDto.events.flatMap { event -> event.attendees.map { it.toEntity() } }
                attendeeDao.upsert(attendees = attendees)
            }

            agendaDto.events.map { it.toEvent(loggedInUserId = loggedInUserId) }.forEach {
                alarmScheduler.schedule(it)
            }
        }
    }

    private suspend fun saveTasks(agendaDto: AgendaResponseDto) {
        if (agendaDto.tasks.isNotEmpty()) {
            val tasks = agendaDto.tasks.map { it.toEntity() }
            val tasksSyncType = tasks.map { TaskSyncEntity(id = it.id, syncType = SyncType.SYNCED) }

            taskDao.upsertSyncStateAndTasks(tasks = tasks, tasksSyncType = tasksSyncType)

            agendaDto.tasks.map { it.toTask() }.forEach {
                alarmScheduler.schedule(it)
            }
        }
    }

    private suspend fun saveReminders(agendaDto: AgendaResponseDto) {
        if (agendaDto.reminders.isNotEmpty()) {
            val reminders = agendaDto.reminders.map { it.toEntity() }
            val remindersSyncType = reminders.map { ReminderSyncEntity(id = it.id, syncType = SyncType.SYNCED) }

            reminderDao.upsertSyncStateAndReminders(reminders = reminders, remindersSyncType = remindersSyncType)

            agendaDto.reminders.map { it.toReminder() }.forEach {
                alarmScheduler.schedule(it)
            }
        }
    }

    companion object {
        private const val ERROR_DOWNLOAD = "No logged in user"
    }
}
