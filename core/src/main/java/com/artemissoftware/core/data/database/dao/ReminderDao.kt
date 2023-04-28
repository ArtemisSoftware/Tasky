package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.ReminderEntity
import com.artemissoftware.core.data.database.entities.ReminderSyncEntity
import com.artemissoftware.core.data.database.entities.relations.ReminderAndSyncState
import com.artemissoftware.core.domain.SyncType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
interface ReminderDao {
    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE id = :id")
    suspend fun getReminderAndSyncState(id: String): ReminderAndSyncState?

    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE time >= :initialDate AND time < :endDate")
    fun getReminders(initialDate: Long, endDate: Long): Flow<List<ReminderAndSyncState>>

    @Transaction
    @Query("SELECT id FROM reminderEntity")
    fun getAllRemindersIds(): List<String>

    @Transaction
    @Query("SELECT * FROM reminderEntity WHERE time >= :currentTime")
    suspend fun getRemindersToSetAlarm(currentTime: Long): List<ReminderAndSyncState>

    @Upsert
    fun upsert(reminderEntity: ReminderEntity)

    @Upsert
    suspend fun upsert(reminderEntities: List<ReminderEntity>)

    @Upsert
    suspend fun upsertReminderSync(reminderSyncEntity: ReminderSyncEntity)

    @Upsert
    suspend fun upsertReminderSync(reminderEntities: List<ReminderSyncEntity>)

    @Transaction
    suspend fun upsertSyncStateAndReminder(reminderEntity: ReminderEntity, reminderSyncEntity: ReminderSyncEntity) {
        upsert(reminderEntity)
        upsertReminderSync(reminderSyncEntity)
    }

    @Transaction
    suspend fun upsertSyncStateAndReminders(reminders: List<ReminderEntity>, remindersSyncType: List<ReminderSyncEntity>) {
        upsert(reminders)
        upsertReminderSync(remindersSyncType)
    }

    @Query("DELETE FROM reminderEntity WHERE id = :id")
    suspend fun deleteReminder(id: String)

    @Delete
    suspend fun deleteAllReminders(reminders: List<ReminderEntity>)

    @Query("DELETE FROM reminderEntity")
    suspend fun deleteAllReminders()

    @Query("DELETE FROM reminderSyncEntity WHERE id IN (:idList)")
    suspend fun deleteSyncState(idList: List<String>)

    @Query("DELETE FROM reminderSyncEntity")
    suspend fun deleteAllSyncState()

    @Transaction
    suspend fun deleteRemindersAndSyncState(initialDate: Long, endDate: Long): List<String> {
        val reminders = getReminders(initialDate = initialDate, endDate = endDate).first()
        deleteAllReminders(reminders = reminders.map { it.reminder })
        deleteSyncState(reminders.map { it.reminder.id })

        return reminders.map { it.reminder.id }
    }

    @Transaction
    suspend fun deleteAll(): List<String> {
        val ids = getAllRemindersIds()
        deleteAllReminders()
        deleteAllSyncState()
        return ids
    }

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, reminderSyncEntity: ReminderSyncEntity) {
        deleteReminder(id)
        upsertReminderSync(reminderSyncEntity)
    }

    @Query("SELECT * FROM reminderSyncEntity WHERE syncType IN (:types)")
    suspend fun getRemindersToSync(types: Array<SyncType> = arrayOf(SyncType.CREATE, SyncType.UPDATE, SyncType.DELETE)): List<ReminderSyncEntity>
}
