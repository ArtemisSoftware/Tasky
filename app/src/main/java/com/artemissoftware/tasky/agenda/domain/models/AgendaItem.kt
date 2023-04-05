package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.agenda.NotificationType
import java.time.LocalDateTime
import java.util.*

sealed class AgendaItem(
    val itemId: String,
    var itemTitle: String,
    var itemDescription: String? = null,
    var itemRemindAt: LocalDateTime,
    var starDate: LocalDateTime,
    var itemNotification: NotificationType,
    val itemSyncState: SyncType,
) {

    data class Reminder(
        val id: String = UUID.randomUUID().toString(),
        var title: String = "",
        var description: String? = null,
        var time: LocalDateTime = LocalDateTime.now(),
        var remindAt: LocalDateTime = NotificationType.remindAt(time, NotificationType.defaultNotification()),
        var notification: NotificationType = NotificationType.getNotification(remindAt = remindAt, startDate = time),
        val syncState: SyncType = SyncType.CREATE,
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemNotification = notification,
        starDate = time,
        itemSyncState = syncState,
    )

    data class Task(
        val id: String = UUID.randomUUID().toString(),
        var title: String = "",
        var description: String? = null,
        var time: LocalDateTime = LocalDateTime.now(),
        var isDone: Boolean = false,
        var remindAt: LocalDateTime = NotificationType.remindAt(time, NotificationType.defaultNotification()),
        var notification: NotificationType = NotificationType.getNotification(remindAt = remindAt, startDate = time),
        val syncState: SyncType = SyncType.CREATE,
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemNotification = notification,
        starDate = time,
        itemSyncState = syncState,
    )

    companion object {
        val mockReminder = Reminder(
            id = "fdfgdf",
            title = "This is a reminder",
            description = "This is the description of the reminder",
            remindAt = LocalDateTime.now(),
            time = LocalDateTime.now(),
            notification = NotificationType.THIRTY_MINUTES_BEFORE,
        )

        val mockTask = Task(
            id = "fdfgdf",
            title = "This is a task",
            description = "This is the description of the task",
            remindAt = LocalDateTime.now(),
            time = LocalDateTime.now(),
        )
    }
}
