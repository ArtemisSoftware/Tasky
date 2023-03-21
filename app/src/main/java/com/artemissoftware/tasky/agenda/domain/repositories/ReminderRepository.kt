package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface ReminderRepository {

    suspend fun getReminder(id: String): AgendaItem.Reminder?

    suspend fun saveReminderAndSync(reminder: AgendaItem.Reminder): DataResponse<Unit>

    suspend fun deleteReminderAndSync(id: String): DataResponse<Unit>
}
