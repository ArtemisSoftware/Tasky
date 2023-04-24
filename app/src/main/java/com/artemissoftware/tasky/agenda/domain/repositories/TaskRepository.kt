package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.domain.models.SyncState
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {

    suspend fun getTask(id: String): AgendaItem.Task?

    fun getTasks(date: LocalDate): Flow<List<AgendaItem.Task>>

    suspend fun saveTaskAndSync(task: AgendaItem.Task): DataResponse<Unit>

    suspend fun deleteTaskAndSync(id: String): DataResponse<Unit>

    suspend fun upsertTasks(tasks: List<AgendaItem.Task>)

    suspend fun getTasksToSync(): List<SyncState>

    suspend fun syncTasksWithRemote(tasks: List<AgendaItem.Task>)
}
