package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import java.time.LocalDate
import javax.inject.Inject

class SyncAgendaUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val agendaRepository: AgendaRepository,
) {

    suspend operator fun invoke(date: LocalDate, loggedInUserId: String) {
        val result = agendaRepository.getAgenda(date, loggedInUserId = loggedInUserId)

        when (result) {
            is DataResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is DataResponse.Success -> {
                result.data?.let {
                    with(it.items) {
                        reminderRepository.upsertReminders(filterIsInstance<AgendaItem.Reminder>())

                        taskRepository.upsertTasks(filterIsInstance<AgendaItem.Task>())

                        eventRepository.upsertEvents(filterIsInstance<AgendaItem.Event>())
                    }
                }
            }
        }
    }
}
