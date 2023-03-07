package com.artemissoftware.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artemissoftware.core.data.database.converters.AgendaItemTypeConverter
import com.artemissoftware.core.data.database.dao.AgendaDao
import com.artemissoftware.core.data.database.dao.NotificationWarningDao
import com.artemissoftware.core.data.database.entities.AgendaItemEntity
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity

@Database(
    entities = [NotificationWarningEntity::class, AgendaItemEntity::class],
    version = 1
)
@TypeConverters(AgendaItemTypeConverter::class)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val notificationWarningDao: NotificationWarningDao

    abstract val agendaDao: AgendaDao
}