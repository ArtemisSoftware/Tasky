package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toDto
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository

class ReminderReminderImpl constructor(
    private val taskyDatabase: TaskyDatabase,
    private val agendaApiSource: AgendaApiSource
): ReminderRepository {

    private val reminderDao = taskyDatabase.reminderDao


    override suspend fun getReminder(id: String): AgendaItem.Reminder {
        return reminderDao.getReminderAndSyncState(id).toAgendaItem()
    }

    override suspend fun save(reminder: AgendaItem.Reminder, syncType: SyncType) {

        if(syncType == SyncType.SYNCED){
            reminderDao.upsertSyncStateAndReminder(reminder.toEntity(), ReminderSyncEntity(id = reminder.id!!, syncType = syncType))
        }
        else{
            reminderDao.upsert(reminder.toEntity())
        }
    }

    override suspend fun syncReminder(reminder: AgendaItem.Reminder, syncType: SyncType): ApiNetworkResponse<Boolean> {

        return try {
            when(syncType){
                SyncType.CREATED -> {
                    agendaApiSource.createReminder(reminder.toDto())
                }
                SyncType.UPDATED -> {
                    agendaApiSource.updateReminder(reminder.toDto())
                }
                else ->{}
            }
            reminderDao.upsertReminderSync(ReminderSyncEntity(id = reminder.id!!, syncType = SyncType.SYNCED))
            ApiNetworkResponse.Success(data = true)
        } catch (ex: TaskyNetworkException) {
            ApiNetworkResponse.Error(exception = ex)
        }
    }

    override suspend fun deleteAndUpdateSyncState(id: String) {
        reminderDao.upsertSyncStateAndDelete(id = id, ReminderSyncEntity(id = id, syncType = SyncType.DELETED))
    }

    override suspend fun syncDelete(id: String): ApiNetworkResponse<Boolean> {

        return try {

            agendaApiSource.deleteReminder(reminderId = id)
            reminderDao.upsertReminderSync(ReminderSyncEntity(id = id, syncType = SyncType.SYNCED))
            ApiNetworkResponse.Success(data = true)

        } catch (ex: TaskyNetworkException) {
            ApiNetworkResponse.Error(exception = ex)
        }
    }

}