package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository

class GetReminderUseCase constructor(
    private val agendaRepository: AgendaRepository
) {

    suspend operator fun invoke(id: String): AgendaItem.Reminder =
        agendaRepository.getReminder(id = id)
}