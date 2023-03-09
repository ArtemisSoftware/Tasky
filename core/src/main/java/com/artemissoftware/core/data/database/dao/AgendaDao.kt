package com.artemissoftware.core.data.database.dao

import androidx.room.*
import com.artemissoftware.core.data.database.entities.ReminderEntity

@Dao
interface AgendaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminderEntity: ReminderEntity)

    @Query("SELECT * FROM reminderEntity WHERE id = :id")
    fun getReminder(id: String): ReminderEntity?

    @Update
    fun update(reminderEntity: ReminderEntity)

    @Query("DELETE FROM reminderEntity WHERE id = :id")
    suspend fun deleteReminder(id: String)
}