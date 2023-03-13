package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.data.SyncType
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface AgendaRepository {

    suspend fun getReminder(id: String): AgendaItem.Reminder

    suspend fun deleteReminder(id: String)

    suspend fun register(reminder: AgendaItem.Reminder, isUpdate: Boolean)

    suspend fun sync(reminder: AgendaItem.Reminder, isUpdate: Boolean): ApiNetworkResponse<Boolean>

    suspend fun syncDelete(reminder: AgendaItem.Reminder): ApiNetworkResponse<Boolean>

    suspend fun updateSyncState(id: String, syncType: SyncType = SyncType.SYNCED)
}