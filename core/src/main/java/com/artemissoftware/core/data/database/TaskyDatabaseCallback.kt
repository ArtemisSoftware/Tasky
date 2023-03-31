package com.artemissoftware.core.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TaskyDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        prePopulateNotifications()
    }
    private fun prePopulateNotifications() {
        CoroutineScope(SupervisorJob()).launch {
            val notifications = listOf(
                NotificationWarningEntity(
                    description = "10 minutes before",
                    minutesBefore = 10,
                    isDefault = false,
                ),
                NotificationWarningEntity(
                    description = "30 minutes before",
                    minutesBefore = 30,
                    isDefault = true,
                ),
                NotificationWarningEntity(
                    description = "1 hour before",
                    minutesBefore = 60,
                    isDefault = false,
                ),
                NotificationWarningEntity(
                    description = "6 hours before",
                    minutesBefore = 60 * 6,
                    isDefault = false,
                ),
                NotificationWarningEntity(
                    description = "1 day before",
                    minutesBefore = 60 * 24,
                    isDefault = false,
                ),
            )

            TaskyDatabase.getInstance(context).notificationWarningDao.insert(notifications)
        }
    }
}
