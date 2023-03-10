package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artemissoftware.core.data.database.entities.NotificationWarningEntity

@Dao
interface NotificationWarningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(times: List<NotificationWarningEntity>)

    @Query("SELECT * FROM notificationWarningEntity ORDER BY minutesBefore ASC")
    fun getNotificationWarnings(): List<NotificationWarningEntity>
}