package com.artemissoftware.tasky.agenda.domain.usecase.task

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository

class GetTaskUseCase constructor(
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(id: String): AgendaItem.Task? =
        taskRepository.getTask(id = id)
}
