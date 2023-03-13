package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.data.SyncType
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository

class DeleteReminderUseCase constructor(
    private val agendaRepository: AgendaRepository
) {

    suspend operator fun invoke(id: String){

        agendaRepository.updateSyncState(id = id, syncType = SyncType.DELETE)
        val reminder = agendaRepository.getReminder(id)
        val result = agendaRepository.syncDelete(reminder)

        when(result){
            is ApiNetworkResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is ApiNetworkResponse.Success -> {
                agendaRepository.deleteReminder(id)
            }
        }
    }

}