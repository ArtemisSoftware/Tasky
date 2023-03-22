package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.SyncType
import java.time.LocalDateTime
import java.util.*

sealed class AgendaItem(
    val itemId: String,
    val itemTitle: String,
    val itemDescription: String? = null,
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
}
