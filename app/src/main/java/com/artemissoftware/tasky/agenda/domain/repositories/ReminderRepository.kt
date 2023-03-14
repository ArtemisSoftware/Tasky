package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface ReminderRepository {

    suspend fun getReminder(id: String): AgendaItem.Reminder?


    suspend fun saveReminderAndSync(reminder: AgendaItem.Reminder): ApiNetworkResponse<Unit>

    suspend fun deleteReminderAndSync(id: String): ApiNetworkResponse<Unit>

}