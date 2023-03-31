package com.artemissoftware.core.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity

data class ReminderAndSyncState(
    @Embedded val reminder: ReminderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val syncState: ReminderSyncEntity,

    @Relation(
        parentColumn = "notificationId",
        entityColumn = "id",
    )
    val notification: NotificationWarningEntity,
)
