package com.artemissoftware.tasky.agenda.data.repositories

import androidx.room.withTransaction
import com.artemissoftware.core.data.database.TaskyDatabase
import com.artemissoftware.core.data.database.dao.AttendeeDao
import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.dao.PictureDao
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.util.extensions.toEndOfDayEpochMilli
import com.artemissoftware.core.util.extensions.toStartOfDayEpochMilli
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class EventRepositoryImpl constructor(
    private val database: TaskyDatabase,
    private val eventDao: EventDao,
    private val pictureDao: PictureDao,
    private val attendeeDao: AttendeeDao,
) : EventRepository {

    override suspend fun getEvent(id: String): AgendaItem.Event? {
        return eventDao.getEventAndSyncState(id)?.toAgendaItem()
    }

    override fun getEvents(date: LocalDate): Flow<List<AgendaItem.Event>> {
        return eventDao.getEvents(initialDate = date.toStartOfDayEpochMilli(), endDate = date.toEndOfDayEpochMilli()).map { list ->
            list.map { item -> item.toAgendaItem() }
        }
    }

    override suspend fun saveEventAndSync(event: AgendaItem.Event) {
        val syncType = if (event.syncState == SyncType.SYNCED) SyncType.UPDATE else event.syncState

        database.withTransaction {
            eventDao.upsertSyncStateAndEvent(eventEntity = event.toEntity(), eventSyncEntity = EventSyncEntity(id = event.id, syncType = syncType))
            pictureDao.upsertPictures(deletedPictures = event.deletedPictures, pictures = event.pictures.map { it.toEntity(eventId = event.id) })
            attendeeDao.upsertAttendees(eventId = event.id, attendees = event.attendees.map { it.toEntity(eventId = event.id) })
        }
    }

    override suspend fun deleteEventAndSync(id: String): DataResponse<Unit> {
        eventDao.upsertSyncStateAndDelete(id = id, EventSyncEntity(id = id, syncType = SyncType.DELETE))

        return try {
            // TODO: complete when remote part is ready. Next PR
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
