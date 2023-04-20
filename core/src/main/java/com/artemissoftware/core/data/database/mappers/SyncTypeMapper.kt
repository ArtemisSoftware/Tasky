package com.artemissoftware.core.data.database.mappers

import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.domain.models.SyncState

fun ReminderSyncEntity.toSyncState(): SyncState {
    return SyncState(
        itemId = id,
        syncType = syncType,
    )
}

fun TaskSyncEntity.toSyncState(): SyncState {
    return SyncState(
        itemId = id,
        syncType = syncType,
    )
}

fun EventSyncEntity.toSyncState(): SyncState {
    return SyncState(
        itemId = id,
        syncType = syncType,
    )
}
