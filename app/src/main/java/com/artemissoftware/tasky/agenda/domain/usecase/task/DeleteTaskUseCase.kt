package com.artemissoftware.tasky.agenda.domain.usecase.task

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository

class DeleteTaskUseCase constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: String) {
        val result = taskRepository.deleteTaskAndSync(id)

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
