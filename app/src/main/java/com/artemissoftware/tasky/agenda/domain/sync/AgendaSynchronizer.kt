package com.artemissoftware.tasky.agenda.domain.sync

import java.util.UUID

interface AgendaSynchronizer {

    fun syncLocalWithRemoteData(): UUID

    fun syncRemoteWithLocalData(): UUID
}
