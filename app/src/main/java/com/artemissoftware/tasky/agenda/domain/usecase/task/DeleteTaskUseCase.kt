package com.artemissoftware.tasky.agenda.domain.usecase.task

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: String) {
        val result = taskRepository.deleteTaskAndSync(id)

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
