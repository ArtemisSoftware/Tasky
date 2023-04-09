package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository

class AgendaRepositoryImpl(
    private val reminderDao: ReminderDao,
    private val taskDao: TaskDao,
) : AgendaRepository {
    override suspend fun getAgendaToUpdateAlarms(currentDate: Long): List<AgendaItem> {

        val reminders = reminderDao.getRemindersToSetAlarm(currentDate = currentDate).map { it.toAgendaItem() }
        val tasks = taskDao.getTasksToSetAlarm(currentDate = currentDate).map { it.toAgendaItem() }

        // TODO : add events when they are ready

        return reminders + tasks
    }
}
