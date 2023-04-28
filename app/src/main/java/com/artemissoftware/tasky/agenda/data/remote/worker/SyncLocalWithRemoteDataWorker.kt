package com.artemissoftware.tasky.agenda.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate

@HiltWorker
class SyncLocalWithRemoteDataWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val reminderRepository: ReminderRepository,
    private val agendaRepository: AgendaRepository,
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val result = agendaRepository.getAgenda(LocalDate.now())

        when (result) {
            is DataResponse.Error -> {
                return getWorkResult(isSuccess = false)
            }
            is DataResponse.Success -> {
                result.data?.let { items ->

                    agendaRepository.deleteLocalAgenda(LocalDate.now())

                    supervisorScope {
                        val reminderJob = launch {
                            reminderRepository.syncRemindersWithRemote(items.filterIsInstance<AgendaItem.Reminder>())
                        }
                        val taskJob = launch {
                            taskRepository.syncTasksWithRemote(items.filterIsInstance<AgendaItem.Task>())
                        }
                        val eventJob = launch {
                            eventRepository.syncEventsWithRemote(items.filterIsInstance<AgendaItem.Event>())
                        }

                        listOf(reminderJob, taskJob, eventJob).forEach { it.join() }
                    }
                    return getWorkResult(isSuccess = true)
                } ?: run {
                    return getWorkResult(isSuccess = false)
                }
            }
        }
    }

    private fun getWorkResult(isSuccess: Boolean): Result {
        return when {
            isSuccess -> {
                Result.success()
            }
            (runAttemptCount < MAX_NUMBER_OF_RETRIES) -> {
                return Result.retry()
            }
            else -> {
                Result.failure()
            }
        }
    }

    companion object {
        private const val MAX_NUMBER_OF_RETRIES = 3
    }
}
