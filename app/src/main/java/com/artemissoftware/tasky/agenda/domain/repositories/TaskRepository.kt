package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface TaskRepository {

    suspend fun getTask(id: String): AgendaItem.Task?

    suspend fun saveTaskAndSync(task: AgendaItem.Task): ApiNetworkResponse<Unit>

    suspend fun deleteTaskAndSync(id: String): ApiNetworkResponse<Unit>
}
