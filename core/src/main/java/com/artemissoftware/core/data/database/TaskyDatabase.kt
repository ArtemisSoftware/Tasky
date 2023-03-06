package com.artemissoftware.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.core.data.database.dao.ReminderTimeDao
import com.artemissoftware.core.data.database.entities.ReminderTimeEntity

@Database(
    entities = [ReminderTimeEntity::class],
    version = 1
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val reminderTimeDao: ReminderTimeDao
}