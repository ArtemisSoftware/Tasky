package com.artemissoftware.tasky.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.tasky.data.database.dao.ReminderTimeDao
import com.artemissoftware.tasky.data.database.entities.ReminderTimeEntity

@Database(
    entities = [ReminderTimeEntity::class],
    version = 1
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val reminderTimeDao: ReminderTimeDao
}