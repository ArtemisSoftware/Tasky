package com.artemissoftware.tasky.util.fakes

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.util.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class FakeReminderRepository : ReminderRepository {

    var returnNetworkError = false
    private var reminders = mutableListOf(FakeData.reminder)

    override suspend fun getReminder(id: String): AgendaItem.Reminder? {
        return reminders.find { it.id == id }
    }

    override fun getReminders(date: LocalDate): Flow<List<AgendaItem.Reminder>> = flow {
        reminders.find { it.time.toLocalDate().isEqual(date) }
    }

    override suspend fun saveReminderAndSync(reminder: AgendaItem.Reminder): DataResponse<Unit> {
        reminders.add(reminder)

        return if (returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else {
            DataResponse.Success(Unit)
        }
    }

    override suspend fun deleteReminderAndSync(id: String): DataResponse<Unit> {
        reminders.find { it.id == id }?.let {
            reminders.removeAt(reminders.indexOf(it))
        }

        return if (returnNetworkError) {
            DataResponse.Error(TaskyNetworkException())
        } else {
            DataResponse.Success(Unit)
        }
    }

    override suspend fun upsertReminders(reminders: List<AgendaItem.Reminder>) {
        this.reminders.addAll(reminders)
    }
}
