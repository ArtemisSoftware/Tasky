package com.artemissoftware.tasky.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artemissoftware.tasky.data.database.entities.ReminderTimeEntity

@Dao
interface ReminderTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(times: List<ReminderTimeEntity>)

    @Query("SELECT * FROM reminderTimeEntity ORDER BY duration ASC")
    fun getReminderTimes(): List<ReminderTimeEntity>
}