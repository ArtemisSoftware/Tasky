package com.artemissoftware.tasky.agenda.data

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.util.FakeData

class FakeReminderRepository : ReminderRepository{

    var returnNetworkError = false
    private var reminders = mutableListOf(FakeData.reminder)


    override suspend fun getReminder(id: String): AgendaItem.Reminder? {
        return reminders.find { it.id == id }
    }

    override suspend fun saveReminderAndSync(reminder: AgendaItem.Reminder): ApiNetworkResponse<Unit> {

        reminders.add(reminder)

        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(Unit)
        }
    }

    override suspend fun deleteReminderAndSync(id: String): ApiNetworkResponse<Unit> {
        reminders.find { it.id == id }?.let {
            reminders.removeAt(reminders.indexOf(it))
        }

        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(Unit)
        }
    }


}