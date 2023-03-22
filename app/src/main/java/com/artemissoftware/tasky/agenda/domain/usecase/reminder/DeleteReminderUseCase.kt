package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository

class DeleteReminderUseCase constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(id: String){


        val result = reminderRepository.deleteReminderAndSync(id)

        when(result){
            is DataResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is DataResponse.Success -> {
                // TODO: should send message to the ui saying the everything went well?
            }
        }
    }
}