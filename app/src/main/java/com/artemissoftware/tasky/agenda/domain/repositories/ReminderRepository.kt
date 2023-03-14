package com.artemissoftware.tasky.agenda.domain.repositories

import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem

interface ReminderRepository {

    suspend fun getReminder(id: String): AgendaItem.Reminder

    suspend fun save(reminder: AgendaItem.Reminder, syncType: SyncType)

    suspend fun syncReminder(reminder: AgendaItem.Reminder, syncType: SyncType): ApiNetworkResponse<Boolean>



    suspend fun deleteAndUpdateSyncState(id: String)


    suspend fun syncDelete(id: String): ApiNetworkResponse<Boolean>

}