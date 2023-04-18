package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.util.extensions.toEndOfDayEpochMilli
import com.artemissoftware.core.util.extensions.toStartOfDayEpochMilli
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toDto
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class ReminderRepositoryImpl constructor(
    private val reminderDao: ReminderDao,
    private val agendaApiSource: AgendaApiSource,
) : ReminderRepository {

    override suspend fun getReminder(id: String): AgendaItem.Reminder? {
        return reminderDao.getReminderAndSyncState(id)?.toAgendaItem()
    }

    override fun getReminders(date: LocalDate): Flow<List<AgendaItem.Reminder>> {
        return reminderDao.getReminders(initialDate = date.toStartOfDayEpochMilli(), endDate = date.toEndOfDayEpochMilli()).map { list ->
            list.map { item -> item.toAgendaItem() }
        }
    }

    override suspend fun saveReminderAndSync(reminder: AgendaItem.Reminder): DataResponse<Unit> {
        val syncType = if (reminder.syncState == SyncType.SYNCED) SyncType.UPDATE else reminder.syncState
        reminderDao.upsertSyncStateAndReminder(reminder.toEntity(), ReminderSyncEntity(id = reminder.id, syncType = syncType))

        return try {
            when (syncType) {
                SyncType.CREATE -> {
                    agendaApiSource.createReminder(reminder.toDto())
                }
                SyncType.UPDATE -> {
                    agendaApiSource.updateReminder(reminder.toDto())
                }
                else -> Unit
            }
            reminderDao.upsertReminderSync(ReminderSyncEntity(id = reminder.id, syncType = SyncType.SYNCED))
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun deleteReminderAndSync(id: String): DataResponse<Unit> {
        reminderDao.upsertSyncStateAndDelete(id = id, ReminderSyncEntity(id = id, syncType = SyncType.DELETE))

        return try {
            agendaApiSource.deleteReminder(reminderId = id)
            reminderDao.upsertReminderSync(ReminderSyncEntity(id = id, syncType = SyncType.SYNCED))
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun upsertReminders(reminders: List<AgendaItem.Reminder>) {
        val result = reminders.map { it.toEntity() }
        reminderDao.upsert(result)
    }
}
