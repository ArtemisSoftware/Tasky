package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
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

    override suspend fun getReminder(id: String): AgendaItem.Reminder? {
        return agendaDao.getReminder(id)?.toAgendaItem()
    }

    override suspend fun deleteReminder(id: String) {
        agendaDao.deleteReminder(id = id)
    }

    override suspend fun register(reminder: AgendaItem.Reminder) {

        val entity = reminder.toEntity(id = UUID.randomUUID().toString())

        with(agendaDao){
            reminder.id?.let {
                insert(entity)
            } ?: run {
                update(entity)
            }
        }
    }
}