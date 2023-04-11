package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.util.extensions.toEpochMilli
import com.artemissoftware.tasky.agenda.data.mappers.toAgenda
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.Agenda
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import java.time.LocalDate

class AgendaRepositoryImpl(
    private val agendaApiSource: AgendaApiSource,
    private val reminderDao: ReminderDao,
    private val taskDao: TaskDao,
) : AgendaRepository {

    override suspend fun getAgenda(date: LocalDate): DataResponse<Agenda> {
        return try {
            val result = agendaApiSource.getAgenda(time = date.toEpochMilli())
            DataResponse.Success(data = result.toAgenda())
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun getAgendaToUpdateAlarms(currentDate: Long): List<AgendaItem> {

        val reminders = reminderDao.getRemindersToSetAlarm(currentTime = currentDate).map { it.toAgendaItem() }
        val tasks = taskDao.getTasksToSetAlarm(currentTime = currentDate).map { it.toAgendaItem() }

        // TODO : add events when they are ready

        return reminders + tasks
    }
}
