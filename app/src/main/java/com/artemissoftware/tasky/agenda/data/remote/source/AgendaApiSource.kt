package com.artemissoftware.tasky.agenda.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.tasky.agenda.data.remote.TaskyAgendaApi
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import okhttp3.ResponseBody
import javax.inject.Inject

class AgendaApiSource @Inject constructor(private val taskyAgendaApi: TaskyAgendaApi) {

    suspend fun createReminder(reminder: ReminderDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.createReminder(reminder)
        }
    }

    suspend fun updateReminder(reminder: ReminderDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.updateReminder(reminder)
        }
    }

    suspend fun deleteReminder(reminderId: String): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteReminder(reminderId)
        }
    }

    suspend fun createTask(task: TaskDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.createTask(task)
        }
    }

    suspend fun updateTask(task: TaskDto): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.updateTask(task)
        }
    }

    suspend fun deleteTask(taskId: String): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteTask(taskId)
        }
    }
}
