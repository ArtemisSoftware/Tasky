package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface AgendaRepository {

    suspend fun getAgendaItem(id: String): AgendaItem?

    suspend fun delete(id: String)

    suspend fun register(agendaItem: AgendaItem)
}