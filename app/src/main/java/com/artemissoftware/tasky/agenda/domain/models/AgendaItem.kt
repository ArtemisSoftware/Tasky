package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.SyncType
import java.time.LocalDateTime
import java.util.*

sealed class AgendaItem(
    val itemId: String,
    var itemTitle: String,
    var itemDescription: String? = null,
    var itemRemindAt: LocalDateTime,
    val itemTime: LocalDateTime,
    var itemNotification: Notification,
    val itemSyncState: SyncType,
) {

    data class Reminder(
        val id: String = UUID.randomUUID().toString(),
        var title: String = "",
        var description: String? = null,
        var notification: Notification,
        var time: LocalDateTime = LocalDateTime.now(),
        var remindAt: LocalDateTime = time.minusMinutes(notification.minutesBefore),
        val syncState: SyncType = SyncType.CREATE,
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemNotification = notification,
        itemTime = time,
        itemSyncState = syncState,
    )

    data class Task(
        val id: String = UUID.randomUUID().toString(),
        var title: String,
        var description: String? = null,
        var remindAt: LocalDateTime,
        var notification: Notification,
        var time: LocalDateTime,
        var isDone: Boolean = false,
        val syncState: SyncType = SyncType.CREATE,
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemNotification = notification,
        itemTime = time,
        itemSyncState = syncState,
    )

    companion object {
        val mockReminder = Reminder(
            id = "fdfgdf",
            title = "This is a reminder",
            description = "This is the description of the reminder",
            remindAt = LocalDateTime.now(),
            time = LocalDateTime.now(),
            notification = Notification(1, 30, "30 minutes before", true),
        )
    }
}
