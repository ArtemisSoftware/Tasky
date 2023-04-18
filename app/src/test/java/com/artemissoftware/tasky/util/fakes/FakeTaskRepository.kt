package com.artemissoftware.tasky.util.fakes

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import com.artemissoftware.tasky.util.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class FakeTaskRepository : TaskRepository {

    var returnNetworkError = false
    private var tasks = mutableListOf(FakeData.task)

    override suspend fun getTask(id: String): AgendaItem.Task? {
        return tasks.find { it.id == id }
    }

    override fun getTasks(date: LocalDate): Flow<List<AgendaItem.Task>> = flow {
        tasks.find { it.time.toLocalDate().isEqual(date) }
    }

    override suspend fun saveTaskAndSync(task: AgendaItem.Task): DataResponse<Unit> {
        tasks.add(task)

        return if (returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else {
            DataResponse.Success(Unit)
        }
    }

    override suspend fun deleteTaskAndSync(id: String): DataResponse<Unit> {
        tasks.find { it.id == id }?.let {
            tasks.removeAt(tasks.indexOf(it))
        }

        return if (returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else {
            DataResponse.Success(Unit)
        }
    }

    override suspend fun upsertTasks(tasks: List<AgendaItem.Task>) {
        this.tasks.addAll(tasks)
    }
}
