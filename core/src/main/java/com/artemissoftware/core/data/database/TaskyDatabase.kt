package com.artemissoftware.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.dao.NotificationWarningDao
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.database.entities.EventAttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.core.data.database.entities.PhotoEntity
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity

@Database(
    entities = [
        NotificationWarningEntity::class,
        ReminderEntity::class,
        ReminderSyncEntity::class,
        TaskEntity::class,
        TaskSyncEntity::class,
        EventEntity::class,
        EventAttendeeEntity::class,
        PhotoEntity::class,
        EventSyncEntity::class,
    ],
    version = 1,
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val notificationWarningDao: NotificationWarningDao
    abstract val reminderDao: ReminderDao
    abstract val taskDao: TaskDao
    abstract val eventDao: EventDao
}
