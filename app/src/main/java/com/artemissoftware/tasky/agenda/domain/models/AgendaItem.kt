package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.agenda.NotificationType
import java.time.LocalDateTime
import java.util.*

sealed class AgendaItem(
    val itemId: String,
    val itemTitle: String,
    val itemDescription: String? = null,
    val itemRemindAt: LocalDateTime,
    val starDate: LocalDateTime,
    val itemNotification: NotificationType,
    val itemSyncState: SyncType,
) {

    data class Reminder(
        val id: String = UUID.randomUUID().toString(),
        var title: String = "",
        var description: String? = null,
        var time: LocalDateTime = LocalDateTime.now(),
        var remindAt: LocalDateTime = NotificationType.remindAt(time, NotificationType.defaultNotification()),
        var notification: NotificationType = NotificationType.getNotification(remindAt = remindAt, startDate = time),
        var syncState: SyncType = SyncType.CREATE,
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
        val title: String = "",
        val description: String? = null,
        val time: LocalDateTime = LocalDateTime.now(),
        val isDone: Boolean = false,
        val remindAt: LocalDateTime = NotificationType.remindAt(time, NotificationType.defaultNotification()),
        val notification: NotificationType = NotificationType.getNotification(remindAt = remindAt, startDate = time),
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

    data class Event( // TODO: this is incomplete. Will be completed in the next PRs
        val id: String = UUID.randomUUID().toString(),
        val title: String = "",
        val description: String? = null,
        var from: LocalDateTime = LocalDateTime.now(),
        var to: LocalDateTime = LocalDateTime.now().plusMinutes(30L),
        val remindAt: LocalDateTime = NotificationType.remindAt(from, NotificationType.defaultNotification()),
        val notification: NotificationType = NotificationType.getNotification(remindAt = remindAt, startDate = from),
        val pictures: List<Picture> = emptyList(),
        val deletedPictures: List<String> = emptyList(),
        val syncState: SyncType = SyncType.CREATE,
        val hostId: String = "",
        val isGoing: Boolean = true,
        val attendees: List<Attendee> = emptyList(),
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemNotification = notification,
        starDate = from,
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
