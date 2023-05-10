package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.tasky.agenda.domain.sync.AgendaSynchronizer
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class SyncAgendaPeriodicallyUseCase @Inject constructor(
    private val agendaSynchronizer: AgendaSynchronizer,
) {
    operator fun invoke(): UUID {
        return agendaSynchronizer.syncAgenda()
    }
}
