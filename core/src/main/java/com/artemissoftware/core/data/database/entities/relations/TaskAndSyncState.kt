package com.artemissoftware.core.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity

data class TaskAndSyncState(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val syncState: TaskSyncEntity,

    @Relation(
        parentColumn = "notificationId",
        entityColumn = "id",
    )
    val notification: NotificationWarningEntity,
)
