package com.artemissoftware.tasky.agenda.domain.sync

import java.time.LocalDate
import java.util.UUID

interface AgendaSynchronizer {

    fun syncAgenda(currentDate: LocalDate): UUID

    fun syncLocalWithRemoteData(): UUID

    fun syncRemoteWithLocalData(): UUID
}
