package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.NotificationWarningDao
import com.artemissoftware.tasky.agenda.data.mappers.toNotification
import com.artemissoftware.tasky.agenda.domain.models.Notification
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository

class AgendaRepositoryImpl(
    private val notificationWarningDao: NotificationWarningDao,
) : AgendaRepository {

    override suspend fun getNotifications(): List<Notification> {
        return notificationWarningDao.getNotificationWarnings().map { it.toNotification() }
    }
}
