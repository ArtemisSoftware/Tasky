package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.SyncType
import java.time.LocalDateTime
import java.util.*

sealed class AgendaItem(
    val itemId: String,
    var itemTitle: String,
    var itemDescription: String? = null,
    val itemRemindAt: LocalDateTime,
    val itemTime: LocalDateTime,
    val itemSyncState: SyncType,
) {

    data class Reminder(
        val id: String = UUID.randomUUID().toString(),
        var title: String,
        var description: String? = null,
        var remindAt: LocalDateTime,
        var time: LocalDateTime,
        val syncState: SyncType = SyncType.CREATE,
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemTime = time,
        itemSyncState = syncState,
    )

    data class Task(
        val id: String = UUID.randomUUID().toString(),
        var title: String,
        var description: String? = null,
        var remindAt: LocalDateTime,
        var time: LocalDateTime,
        var isDone: Boolean = false,
        val syncState: SyncType = SyncType.CREATE,
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
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
        )
    }
}
