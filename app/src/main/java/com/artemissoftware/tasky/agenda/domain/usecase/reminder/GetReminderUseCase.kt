package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import javax.inject.Inject

class GetReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {

    suspend operator fun invoke(id: String): AgendaItem.Reminder? =
        reminderRepository.getReminder(id = id)
}