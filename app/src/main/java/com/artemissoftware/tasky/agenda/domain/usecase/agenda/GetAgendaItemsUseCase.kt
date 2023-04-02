package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import javax.inject.Inject

class GetAgendaItemsUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
) {
    suspend operator fun invoke(): List<AgendaItem> { // TODO: add date to search

        val list = listOf<AgendaItem>(
            reminderRepository.getReminder("7706f862-e36c-4676-bd75-8f4329402f8f")!!, // TODO: metodo para obter todos
        )

        return list
    }
}
