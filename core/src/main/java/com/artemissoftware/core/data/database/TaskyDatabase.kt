package com.artemissoftware.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.core.data.database.dao.AttendeeDao
import com.artemissoftware.core.data.database.dao.EventDao
import com.artemissoftware.core.data.database.dao.PictureDao
import com.artemissoftware.core.data.database.dao.ReminderDao
import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.database.entities.AttendeeEntity
import com.artemissoftware.core.data.database.entities.EventEntity
import com.artemissoftware.core.data.database.entities.EventSyncEntity
import com.artemissoftware.core.data.database.entities.PictureEntity
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
        EventEntity::class,
        EventSyncEntity::class,
        PictureEntity::class,
        AttendeeEntity::class,
    ],
    version = 1,
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val reminderDao: ReminderDao
    abstract val taskDao: TaskDao
    abstract val eventDao: EventDao
    abstract val attendeeDao: AttendeeDao
    abstract val pictureDao: PictureDao
}
