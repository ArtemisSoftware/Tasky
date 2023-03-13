package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.SyncType
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toDto
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import java.util.*

class AgendaRepositoryImpl constructor(
    private val taskyDatabase: TaskyDatabase,
    private val agendaApiSource: AgendaApiSource
): AgendaRepository {

    private val agendaDao = taskyDatabase.agendaDao

    override suspend fun getReminder(id: String): AgendaItem.Reminder {
        return agendaDao.getReminder(id).toAgendaItem()
    }

    override suspend fun deleteReminder(id: String) {
        agendaDao.deleteReminder(id = id)
    }

    override suspend fun register(reminder: AgendaItem.Reminder, isUpdate: Boolean) {

        val entity = reminder.toEntity(if(isUpdate) SyncType.UPDATE else SyncType.CREATE)

        with(agendaDao){
            if(isUpdate) update(entity) else insert(entity)
        }
    }

    override suspend fun sync(reminder: AgendaItem.Reminder, isUpdate: Boolean): ApiNetworkResponse<Boolean> {

        val reminderDto = reminder.toDto()

        with(agendaApiSource) {
            return try {

                if (isUpdate) updateReminder(reminderDto) else createReminder(reminderDto)
                ApiNetworkResponse.Success(data = true)

            } catch (ex: TaskyNetworkException) {
                ApiNetworkResponse.Error(exception = ex)
            }
        }
    }
    override suspend fun syncDelete(reminder: AgendaItem.Reminder): ApiNetworkResponse<Boolean> {

        with(agendaApiSource) {
            return try {

                deleteReminder(id = reminder.id)
                ApiNetworkResponse.Success(data = true)

            } catch (ex: TaskyNetworkException) {
                ApiNetworkResponse.Error(exception = ex)
            }
        }
    }

    override suspend fun updateSyncState(id: String, syncType: SyncType) {
        agendaDao.updateReminderSync(id = id, syncType = syncType)
    }

}