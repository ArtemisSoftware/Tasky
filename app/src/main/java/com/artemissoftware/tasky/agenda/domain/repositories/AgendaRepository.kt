package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface AgendaRepository {

    suspend fun getAgendaToUpdateAlarms(currentDate: Long): List<AgendaItem>
}
