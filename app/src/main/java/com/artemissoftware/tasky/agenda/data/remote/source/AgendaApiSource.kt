package com.artemissoftware.tasky.agenda.data.remote.source

import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import okhttp3.ResponseBody

interface AgendaApiSource {

    suspend fun createReminder(reminder: ReminderDto): ResponseBody

    suspend fun updateReminder(reminder: ReminderDto): ResponseBody

    suspend fun deleteReminder(reminderId: String): ResponseBody

    suspend fun createTask(task: TaskDto): ResponseBody

    suspend fun updateTask(task: TaskDto): ResponseBody

    suspend fun deleteTask(taskId: String): ResponseBody
}
