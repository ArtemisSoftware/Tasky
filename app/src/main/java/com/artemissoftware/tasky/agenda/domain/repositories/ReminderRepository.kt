package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ReminderRepository {

    suspend fun getReminder(id: String): AgendaItem.Reminder?

    fun getReminders(date: LocalDate): Flow<List<AgendaItem.Reminder>>

    suspend fun saveReminderAndSync(reminder: AgendaItem.Reminder): DataResponse<Unit>

    suspend fun deleteReminderAndSync(id: String): DataResponse<Unit>

    suspend fun upsertReminders(reminders: List<AgendaItem.Reminder>)
}
