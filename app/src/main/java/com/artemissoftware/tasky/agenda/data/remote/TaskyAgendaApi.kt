package com.artemissoftware.tasky.agenda.data.remote

import android.icu.util.TimeZone
import com.artemissoftware.tasky.agenda.data.remote.dto.*
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskyAgendaApi {

    @GET("agenda")
    suspend fun getAgenda(
        @Query(value = "timezone") timezone: String = TimeZone.getDefault().id,
        @Query(value = "time") time: Long = System.currentTimeMillis(),
    ): AgendaResponseDto

    @POST("syncAgenda")
    suspend fun syncAgenda(@Body agenda: AgendaBodyDto): ResponseBody

    @POST("task")
    suspend fun createTask(@Body task: TaskDto): ResponseBody

    @PUT("task")
    suspend fun updateTask(@Body task: TaskDto): ResponseBody

    @DELETE("task")
    suspend fun deleteTask(@Query(value = "taskId") taskId: String): ResponseBody

    @POST("reminder")
    suspend fun createReminder(@Body reminder: ReminderDto): ResponseBody

    @PUT("reminder")
    suspend fun updateReminder(@Body reminder: ReminderDto): ResponseBody

    @DELETE("reminder")
    suspend fun deleteReminder(@Query(value = "reminderId") reminderId: String): ResponseBody

    @GET("attendee")
    suspend fun getAttendee(@Query(value = "email") email: String): AttendeeDto
}
