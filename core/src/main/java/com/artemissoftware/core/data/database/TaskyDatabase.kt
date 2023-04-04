package com.artemissoftware.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity

@Database(
    entities = [
        ReminderEntity::class,
        ReminderSyncEntity::class,
        TaskEntity::class,
        TaskSyncEntity::class,
    ],
    version = 1,
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val reminderDao: ReminderDao
    abstract val taskDao: TaskDao
}
