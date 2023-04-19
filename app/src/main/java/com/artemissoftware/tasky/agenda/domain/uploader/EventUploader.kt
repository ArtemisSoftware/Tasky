package com.artemissoftware.tasky.agenda.domain.uploader

import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface EventUploader {
    suspend fun upload(event: AgendaItem.Event, syncType: SyncType)
}
