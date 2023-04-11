package com.artemissoftware.tasky.agenda.domain.usecase.task

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmScheduler: AlarmScheduler,
) {
    suspend operator fun invoke(
        task: AgendaItem.Task,
    ) {
        val result = taskRepository.saveTaskAndSync(task = task)
        alarmScheduler.schedule(item = task)

        when (result) {
            is DataResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is DataResponse.Success -> {
                // TODO: should send message to the ui saying the everything went well?
            }
        }
    }
}
