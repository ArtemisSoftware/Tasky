package com.artemissoftware.tasky.agenda.data.remote

import android.icu.util.TimeZone
import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaBodyDto
import com.artemissoftware.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskyAgendaApi {

    @GET("agenda")
    suspend fun getAgenda(
        @Query(value = "timezone") timezone: String = TimeZone.getDefault().id,
        @Query(value = "time") time: Long = System.currentTimeMillis()
    ): AgendaResponseDto

    @POST("syncAgenda")
    suspend fun syncAgenda(@Body agenda: AgendaBodyDto): ResponseBody

    @POST("task")
    suspend fun createTask(@Body task: TaskDto): ResponseBody

    @PUT("task")
    suspend fun updateTask(@Body task: TaskDto): ResponseBody
}