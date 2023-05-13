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
    private val agendaSynchronizer: AgendaSynchronizer,
) {

    operator fun invoke() {
        agendaSynchronizer.syncLocalWithRemoteData()
    }
}
