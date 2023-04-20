package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.relations.ReminderAndSyncState
import com.artemissoftware.core.domain.SyncType
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE id = :id")
    suspend fun getReminderAndSyncState(id: String): ReminderAndSyncState?

    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE time >= :initialDate AND time < :endDate")
    fun getReminders(initialDate: Long, endDate: Long): Flow<List<ReminderAndSyncState>>

    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE time >= :currentTime")
    suspend fun getRemindersToSetAlarm(currentTime: Long): List<ReminderAndSyncState>

    @Upsert
    fun upsert(reminderEntity: ReminderEntity)

    @Upsert
    suspend fun upsert(reminderEntities: List<ReminderEntity>)

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

    @Query("SELECT * FROM reminderSyncEntity syncType IN (:types)")
    suspend fun getRemindersToSync(types: Array<SyncType> = arrayOf(SyncType.CREATE, SyncType.UPDATE, SyncType.DELETE)): List<ReminderSyncEntity>
}
