package com.artemissoftware.tasky.util.fakes

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import com.artemissoftware.tasky.util.FakeData

class FakeTaskRepository: TaskRepository {

    var returnNetworkError = false
    private var tasks = mutableListOf(FakeData.task)


    override suspend fun getTask(id: String): AgendaItem.Task? {
        return tasks.find { it.id == id }
    }

    override suspend fun saveTaskAndSync(task: AgendaItem.Task): ApiNetworkResponse<Unit> {
        tasks.add(task)

        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(Unit)
        }
    }

    override suspend fun deleteTaskAndSync(id: String): ApiNetworkResponse<Unit> {
        tasks.find { it.id == id }?.let {
            tasks.removeAt(tasks.indexOf(it))
        }

        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(Unit)
        }
    }
}