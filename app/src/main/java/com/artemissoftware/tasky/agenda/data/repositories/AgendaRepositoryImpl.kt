package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.util.extensions.toEndOfDayEpochMilli
import com.artemissoftware.core.util.extensions.toEpochMilli
import com.artemissoftware.core.util.extensions.toStartOfDayEpochMilli
import com.artemissoftware.tasky.agenda.data.mappers.toAgenda
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import java.time.LocalDate

class AgendaRepositoryImpl(
    private val agendaApiSource: AgendaApiSource,
    private val reminderDao: ReminderDao,
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val alarmScheduler: AlarmScheduler,
) : AgendaRepository {

    override suspend fun getAgenda(date: LocalDate): DataResponse<List<AgendaItem>> {
        return try {
            val result = agendaApiSource.getAgenda(time = date.toEpochMilli())
            DataResponse.Success(data = result.toAgenda())
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun getFutureAgenda(currentDate: Long): List<AgendaItem> {
        val reminders = reminderDao.getRemindersToSetAlarm(currentTime = currentDate).map { it.toAgendaItem() }
        val tasks = taskDao.getTasksToSetAlarm(currentTime = currentDate).map { it.toAgendaItem() }
        val events = eventDao.getEventsToSetAlarm(currentTime = currentDate).map { it.toAgendaItem() }

        return reminders + tasks + events
    }

    override suspend fun logOut(): DataResponse<Unit> {
        return try {
            agendaApiSource.logOut()
            DataResponse.Success(data = Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun deleteLocalAgenda(date: LocalDate) {
        val initialDate = date.toStartOfDayEpochMilli()
        val endDate = date.toEndOfDayEpochMilli()
        val deletedRemindersId = reminderDao.deleteRemindersAndSyncState(initialDate = initialDate, endDate = endDate)
        val deletedTasksId = taskDao.deleteTasksAndSyncState(initialDate = initialDate, endDate = endDate)
        val deletedEventsId = eventDao.deleteEventsAndSyncState(initialDate = initialDate, endDate = endDate)

        (deletedRemindersId + deletedTasksId + deletedEventsId).forEach { id ->
            alarmScheduler.cancel(id)
        }
    }
}
