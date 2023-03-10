package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface AgendaRepository {

    suspend fun getReminder(id: String): AgendaItem.Reminder

    suspend fun deleteReminder(id: String)

    suspend fun register(reminder: AgendaItem.Reminder)
}