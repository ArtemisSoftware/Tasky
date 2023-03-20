package com.artemissoftware.tasky.agenda.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.tasky.agenda.data.remote.TaskyAgendaApi
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import okhttp3.ResponseBody

class AgendaApiSourceImpl constructor(private val taskyAgendaApi: TaskyAgendaApi) :
    AgendaApiSource {

    override suspend fun createReminder(reminder: ReminderDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.createReminder(reminder)
        }
    }

    override suspend fun updateReminder(reminder: ReminderDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.updateReminder(reminder)
        }
    }

    override suspend fun deleteReminder(reminderId: String): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteReminder(reminderId)
        }
    }

    override suspend fun createTask(task: TaskDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.createTask(task)
        }
    }

    override suspend fun updateTask(task: TaskDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.updateTask(task)
        }
    }

    override suspend fun deleteTask(taskId: String): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteTask(taskId)
        }
    }


}