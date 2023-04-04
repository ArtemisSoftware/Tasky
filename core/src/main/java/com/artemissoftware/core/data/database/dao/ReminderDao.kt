package com.artemissoftware.core.data.database.dao

import androidx.room.*
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.relations.ReminderAndSyncState

@Dao
interface ReminderDao {
    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE id = :id")
    fun getReminderAndSyncState(id: String): ReminderAndSyncState?

    @Upsert
    fun upsert(reminderEntity: ReminderEntity)

    @Upsert
    suspend fun upsertReminderSync(reminderSyncEntity: ReminderSyncEntity)

    @Transaction
    suspend fun upsertSyncStateAndReminder(reminderEntity: ReminderEntity, reminderSyncEntity: ReminderSyncEntity) {
        upsert(reminderEntity)
        upsertReminderSync(reminderSyncEntity)
    }

    @Query("DELETE FROM reminderEntity WHERE id = :id")
    suspend fun deleteReminder(id: String)

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, reminderSyncEntity: ReminderSyncEntity) {
        deleteReminder(id)
        upsertReminderSync(reminderSyncEntity)
    }

    @Query("SELECT EXISTS(SELECT * FROM reminderEntity WHERE id = :id)")
    fun reminderExists(id: String): Boolean
}
