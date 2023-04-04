package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.NotificationWarningDao
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.util.extensions.toEpochMilli
import com.artemissoftware.tasky.agenda.data.mappers.toAgenda
import com.artemissoftware.tasky.agenda.data.mappers.toNotification
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.Agenda
import com.artemissoftware.tasky.agenda.domain.models.Notification
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import java.time.LocalDate

class AgendaRepositoryImpl(
    private val agendaApiSource: AgendaApiSource,
) : AgendaRepository {

    override suspend fun getAgenda(date: LocalDate): DataResponse<Agenda> {
        return try {
            val result = agendaApiSource.getAgenda(time = date.toEpochMilli())
            DataResponse.Success(data = result.toAgenda())
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
