package com.artemissoftware.tasky.agenda.data.remote.source

import com.artemissoftware.tasky.agenda.data.remote.dto.ReminderDto
import com.artemissoftware.tasky.authentication.data.remote.dto.RegistrationBodyDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Query

interface AgendaApiSource {

    suspend fun createReminder(reminder: ReminderDto): ResponseBody

    suspend fun updateReminder(@Body reminder: ReminderDto): ResponseBody

    suspend fun deleteReminder(@Query(value = "reminderId") reminderId: String): ResponseBody
}