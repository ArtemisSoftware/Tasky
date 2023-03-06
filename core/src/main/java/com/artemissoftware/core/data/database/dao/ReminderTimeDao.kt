package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artemissoftware.core.data.database.entities.ReminderTimeEntity

@Dao
interface ReminderTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(times: List<ReminderTimeEntity>)

    @Query("SELECT * FROM reminderTimeEntity ORDER BY durationInMinutes ASC")
    fun getReminderTimes(): List<ReminderTimeEntity>
}