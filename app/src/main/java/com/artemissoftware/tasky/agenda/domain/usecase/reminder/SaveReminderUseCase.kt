package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import java.util.*

class SaveReminderUseCase constructor(
    private val reminderRepository: ReminderRepository
){

    suspend operator fun invoke(
        reminder: AgendaItem.Reminder
    ) {

        val syncType = if(reminder.id == null) SyncType.CREATED else SyncType.UPDATED
        val id = reminder.id ?: UUID.randomUUID().toString()
        reminder.id = id

        reminderRepository.save(reminder = reminder, syncType)
        val result = reminderRepository.syncReminder(reminder = reminder, syncType = syncType)

        when(result){
            is ApiNetworkResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is ApiNetworkResponse.Success -> {
                // TODO: should send message to the ui saying the everything went well?
            }
        }
    }
}