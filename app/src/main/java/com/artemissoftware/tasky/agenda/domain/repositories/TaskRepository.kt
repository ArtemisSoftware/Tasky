package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface TaskRepository {

    suspend fun getTask(id: String): AgendaItem.Task?

    suspend fun saveTaskAndSync(task: AgendaItem.Task): DataResponse<Unit>

    suspend fun deleteTaskAndSync(id: String): DataResponse<Unit>
}
