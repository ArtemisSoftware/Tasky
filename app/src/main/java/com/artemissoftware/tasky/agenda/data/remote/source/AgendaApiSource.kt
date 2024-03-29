package com.artemissoftware.tasky.agenda.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.core.data.remote.api.TaskyAuthenticationApi
import com.artemissoftware.tasky.agenda.data.remote.TaskyAgendaApi
import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.data.remote.dto.AttendeeDto
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import javax.inject.Inject

class AgendaApiSource @Inject constructor(
    private val taskyAgendaApi: TaskyAgendaApi,
    private val taskyAuthenticationApi: TaskyAuthenticationApi,
) {

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

    suspend fun getAttendee(email: String): AttendeeDto {
        return HandleApi.safeApiCall {
            taskyAgendaApi.getAttendee(email)
        }
    }

    suspend fun deleteAttendee(eventId: String) {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteAttendee(id = eventId)
        }
    }

    suspend fun getAgenda(time: Long): AgendaResponseDto {
        return HandleApi.safeApiCall {
            taskyAgendaApi.getAgenda(time = time)
        }
    }

    suspend fun getFullAgenda(): AgendaResponseDto {
        return HandleApi.safeApiCall {
            taskyAgendaApi.getFullAgenda()
        }
    }

    suspend fun logOut(): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAuthenticationApi.logoutUser()
        }
    }

    suspend fun createEvent(eventBody: MultipartBody.Part, pictures: List<MultipartBody.Part>) {
        return HandleApi.safeApiCall {
            taskyAgendaApi.createEvent(body = eventBody, files = pictures)
        }
    }

    suspend fun updateEvent(eventBody: MultipartBody.Part, pictures: List<MultipartBody.Part>) {
        return HandleApi.safeApiCall {
            taskyAgendaApi.updateEvent(body = eventBody, files = pictures)
        }
    }

    suspend fun deleteEvent(eventId: String) {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteEvent(eventId)
        }
    }
}
