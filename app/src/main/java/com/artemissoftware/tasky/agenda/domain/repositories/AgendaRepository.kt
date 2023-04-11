package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.Agenda
import java.time.LocalDate

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface AgendaRepository {

    suspend fun getAgenda(date: LocalDate): DataResponse<Agenda>

    suspend fun getFutureAgenda(currentDate: Long): List<AgendaItem>
}
