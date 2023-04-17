package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository

class EventRepositoryImpl constructor(
    private val eventDao: EventDao,
) : EventRepository {

    override suspend fun getEvent(id: String): AgendaItem.Event? {
        return eventDao.getEventAndSyncState(id)?.toAgendaItem()
    }

    override suspend fun saveEventAndSync(event: AgendaItem.Event): DataResponse<Unit> {
        val syncType = if (event.syncState == SyncType.SYNCED) SyncType.UPDATE else event.syncState
        eventDao.upsertSyncStateAndEvent(
            eventEntity = event.toEntity(),
            attendees = event.attendees.map { it.toEntity(eventId = event.id) },
        )

        return try {
            // TODO: complete when remote part is ready. Next PR

            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
