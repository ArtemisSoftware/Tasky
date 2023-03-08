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

    override suspend fun getAgendaItem(id: String): AgendaItem? {
        return agendaDao.getAgendaItem(id)?.toAgendaItem()
    }

    override suspend fun delete(id: String) {
        agendaDao.delete(id = id)
    }

    override suspend fun register(agendaItem: AgendaItem) {

        val entity = agendaItem.toEntity(id = UUID.randomUUID().toString())

        with(agendaDao){
            agendaItem.id?.let {
                insert(entity)
            } ?: run {
                update(entity)
            }
        }
    }
}