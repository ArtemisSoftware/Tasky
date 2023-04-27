package com.artemissoftware.tasky.agenda.domain.usecase.agenda

import com.artemissoftware.tasky.agenda.domain.sync.AgendaSynchronizer
import java.util.UUID
import javax.inject.Inject

class SyncRemoteWithLocalDataUseCase @Inject constructor(
    private val agendaSynchronizer: AgendaSynchronizer,
) {
    operator fun invoke(): UUID {
        return agendaSynchronizer.syncRemoteWithLocalData()
    }
}
