package com.artemissoftware.tasky.agenda.data

import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.util.FakeData

class FakeReminderRepository : ReminderRepository{

    var returnNetworkError = false
    private var reminders = mutableListOf(FakeData.reminder)

    override suspend fun getReminder(id: String): AgendaItem.Reminder {
        return reminders[0]
    }

    override suspend fun deleteReminder(id: String) {
        reminders.removeAt(0)
    }

    override suspend fun register(reminder: AgendaItem.Reminder, isUpdate: Boolean) {
        reminders.add(reminder)
    }

    override suspend fun sync(reminder: AgendaItem.Reminder, isUpdate: Boolean): ApiNetworkResponse<Boolean> {
        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(true)
        }
    }

    override suspend fun syncDelete(reminder: AgendaItem.Reminder): ApiNetworkResponse<Boolean> {
        return if(returnNetworkError) {
            ApiNetworkResponse.Error(TaskyNetworkException())
        } else {
            ApiNetworkResponse.Success(true)
        }
    }

    override suspend fun updateSyncState(id: String, syncType: SyncType) {
        // TODO("Not yet implemented")
    }

}