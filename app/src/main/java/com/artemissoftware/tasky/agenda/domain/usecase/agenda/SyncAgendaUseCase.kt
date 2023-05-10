package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import com.artemissoftware.tasky.agenda.domain.sync.AgendaSynchronizer
import java.time.LocalDate
import javax.inject.Inject

class SyncAgendaUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val agendaRepository: AgendaRepository,
    private val agendaSynchronizer: AgendaSynchronizer,
) {

    operator fun invoke(/*date: LocalDate*/) {
        agendaSynchronizer.syncLocalWithRemoteData()
        /*
        val result = agendaRepository.getAgenda(date)

        when (result) {
            is DataResponse.Error -> {
                // TODO: should send message to the ui saying the sync failed?
            }
            is DataResponse.Success -> {
                result.data?.let { items ->

                    with(items) {
                        reminderRepository.syncRemindersWithRemote(filterIsInstance<AgendaItem.Reminder>())
                        taskRepository.syncTasksWithRemote(filterIsInstance<AgendaItem.Task>())
                        eventRepository.syncEventsWithRemote(filterIsInstance<AgendaItem.Event>(), refreshPictures = true)
                    }
                }
            }
        }
        */
    }
}
