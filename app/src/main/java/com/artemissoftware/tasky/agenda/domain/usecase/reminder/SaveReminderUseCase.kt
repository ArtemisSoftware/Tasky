package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository

class SaveReminderUseCase constructor(
    private val reminderRepository: ReminderRepository
){

    suspend operator fun invoke(
        reminder: AgendaItem.Reminder
    ) {

        val result = reminderRepository.saveReminderAndSync(reminder = reminder)

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