package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface EventRepository {

    suspend fun getEvent(id: String): AgendaItem.Event?

    suspend fun saveEventAndSync(event: AgendaItem.Event): DataResponse<Unit>
}
