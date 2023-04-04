package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetAgendaItemsUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(date: LocalDate): Flow<List<AgendaItem>> = combine(
        reminderRepository.getReminders(date = date),
        taskRepository.getTasks(date = date),
    ) { reminders, tasks ->

        (reminders + tasks).sortedBy { it.itemTime }
    }
}
