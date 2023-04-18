package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventRepository {

    suspend fun getEvent(id: String): AgendaItem.Event?

    fun getEvents(date: LocalDate): Flow<List<AgendaItem.Event>>

    suspend fun saveEventAndSync(event: AgendaItem.Event): DataResponse<Unit>

    suspend fun deleteEventAndSync(id: String): DataResponse<Unit>
}
