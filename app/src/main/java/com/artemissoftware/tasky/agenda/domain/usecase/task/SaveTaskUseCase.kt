package com.artemissoftware.tasky.agenda.domain.usecase.task

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository

class SaveTaskUseCase constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        task: AgendaItem.Task,
    ) {
        val result = taskRepository.saveTaskAndSync(task = task)

        when (result) {
            is ApiNetworkResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is ApiNetworkResponse.Success -> {
                // TODO: should send message to the ui saying the everything went well?
            }
        }
    }
}
