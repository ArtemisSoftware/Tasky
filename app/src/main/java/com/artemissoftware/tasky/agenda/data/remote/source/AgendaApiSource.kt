package com.artemissoftware.tasky.agenda.data.remote.source

import com.artemissoftware.core.data.remote.HandleApi
import com.artemissoftware.tasky.agenda.data.remote.TaskyAgendaApi
import com.artemissoftware.tasky.agenda.data.remote.dto.EventCreateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.EventUpdateBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

class AgendaApiSource constructor(private val taskyAgendaApi: TaskyAgendaApi) {

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

    suspend fun createEvent(eventBodyDto: EventCreateBodyDto, imageFiles: List<File>): ResponseBody {
        val event = MultipartBody
            .Part
            .createFormData("create_event_request", eventBodyDto.toString())

        val files = imageFiles.mapIndexed { index, file ->
            MultipartBody.Part
                .createFormData(
                    name = "photo_$index",
                    filename = file.name,
                    body = file.asRequestBody(),
                )
        }

        return HandleApi.safeApiCall {
            taskyAgendaApi.createEvent(body = event, files = files)
        }
    }

    suspend fun updateEvent(eventBodyDto: EventUpdateBodyDto, imageFiles: List<File>): ResponseBody {
        val event = MultipartBody
            .Part
            .createFormData("update_event_request", eventBodyDto.toString())

        val files = imageFiles.mapIndexed { index, file ->
            MultipartBody.Part
                .createFormData(
                    name = "photo_$index",
                    filename = file.name,
                    body = file.asRequestBody(),
                )
        }

        return HandleApi.safeApiCall {
            taskyAgendaApi.updateEvent(body = event, files = files)
        }
    }

    suspend fun deleteEvent(eventId: String): ResponseBody {
        return HandleApi.safeApiCall {
            taskyAgendaApi.deleteEvent(eventId)
        }
    }
}
