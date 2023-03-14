package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.SyncType
import java.time.LocalDateTime

sealed class AgendaItem(
    val itemId: String? = null,
    val itemTitle: String,
    val itemDescription: String? = null,
    val itemRemindAt: LocalDateTime,
    val itemTime: LocalDateTime,
    val itemSyncState: SyncType
){

    data class Reminder(
        var id: String? = null,
        var title: String,
        var description: String? = null,
        var remindAt: LocalDateTime,
        var time: LocalDateTime,
        val syncState: SyncType
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemTime = time,
        itemSyncState = syncState
    )




}
