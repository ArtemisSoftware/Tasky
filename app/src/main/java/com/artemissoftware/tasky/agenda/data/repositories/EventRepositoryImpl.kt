package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.data.mappers.*
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository

class EventRepositoryImpl constructor(
    private val eventDao: EventDao,
    private val agendaApiSource: AgendaApiSource,
) : EventRepository {

    override suspend fun getEvent(id: String): AgendaItem.Event? {
        return eventDao.getEventAndSyncState(id)?.toAgendaItem()
    }

    override suspend fun saveEventAndSync(event: AgendaItem.Event): DataResponse<Unit> {
        val syncType = if (event.syncState == SyncType.SYNCED) SyncType.UPDATE else event.syncState
        eventDao.upsertSyncStateAndEvent(
            eventEntity = event.toEntity(),
            photoEntities = event.toPhotoEntities(),
            eventAttendeeEntities = event.toAttendeeEntities(),
            eventSyncEntity = EventSyncEntity(id = event.id, syncType = syncType),
        )

        val imageFiles = event.photos.filter { it.local != null }.map { it.local!! }

        return try {
            when (syncType) {
                SyncType.CREATE -> {
                    agendaApiSource.createEvent(event.toCreateBodyDto(), imageFiles = imageFiles)
                }
                SyncType.UPDATE -> {
                    agendaApiSource.updateEvent(event.toUpdateBodyDto(), imageFiles = imageFiles)
                }
                else -> Unit
            }
            eventDao.upsertEventSync(EventSyncEntity(id = event.id, syncType = SyncType.SYNCED))
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun deleteEventAndSync(id: String): DataResponse<Unit> {
        eventDao.upsertSyncStateAndDelete(id = id, EventSyncEntity(id = id, syncType = SyncType.DELETE))

        return try {
            agendaApiSource.deleteReminder(reminderId = id)
            eventDao.upsertEventSync(EventSyncEntity(id = id, syncType = SyncType.SYNCED))
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
