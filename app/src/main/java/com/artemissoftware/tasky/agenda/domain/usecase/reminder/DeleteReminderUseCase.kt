package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository

class DeleteReminderUseCase constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(id: String){

        reminderRepository.deleteAndUpdateSyncState(id = id)
        val result = reminderRepository.syncDelete(id)

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