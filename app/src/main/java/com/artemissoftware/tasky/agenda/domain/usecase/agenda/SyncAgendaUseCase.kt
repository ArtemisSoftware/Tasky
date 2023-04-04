package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import java.time.LocalDate
import javax.inject.Inject

class SyncAgendaUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val agendaRepository: AgendaRepository,
) {

    suspend operator fun invoke(date: LocalDate) {
        val result = agendaRepository.getAgenda(date)

        when (result) {
            is DataResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is DataResponse.Success -> {
                result.data?.let {
                    reminderRepository.upsertReminders(it.reminders)

                    // TODO: add task

                    // TODO: add events
                }
            }
        }
    }
}
