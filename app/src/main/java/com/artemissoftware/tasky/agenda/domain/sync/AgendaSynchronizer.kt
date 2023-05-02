package com.artemissoftware.tasky.agenda.domain.sync

import java.time.LocalDate
import java.util.UUID

interface AgendaSynchronizer {

    fun syncAgenda(): UUID

    fun syncLocalWithRemoteData(): UUID

    fun syncRemoteWithLocalData(): UUID
}
