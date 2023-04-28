package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.tasky.agenda.domain.sync.AgendaSynchronizer
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class SyncAgendaUseCase @Inject constructor(
    private val agendaSynchronizer: AgendaSynchronizer,
) {
    operator fun invoke(date: LocalDate): UUID {
        return agendaSynchronizer.syncAgenda(currentDate = date)
    }
}
