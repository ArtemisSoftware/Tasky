package com.artemissoftware.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.artemissoftware.core.data.database.dao.AgendaDao
import com.artemissoftware.core.data.database.dao.NotificationWarningDao
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import com.artemissoftware.core.data.database.entities.ReminderEntity

@Database(
    entities = [NotificationWarningEntity::class, ReminderEntity::class],
    version = 1
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val notificationWarningDao: NotificationWarningDao

    abstract val agendaDao: AgendaDao
}