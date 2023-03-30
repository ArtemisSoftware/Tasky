package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.tasky.agenda.domain.models.Notification

interface AgendaRepository {

    suspend fun getNotifications(): List<Notification>
}