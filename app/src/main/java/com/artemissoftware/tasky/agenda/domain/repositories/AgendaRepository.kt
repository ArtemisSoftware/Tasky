package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.Agenda
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import java.time.LocalDate

interface AgendaRepository {

    suspend fun getAgenda(date: LocalDate, loggedInUserId: String): DataResponse<Agenda>

    suspend fun getFutureAgenda(currentDate: Long): List<AgendaItem>

    suspend fun logOut(): DataResponse<Unit>
}
