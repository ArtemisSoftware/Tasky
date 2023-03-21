package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.data.database.entities.relations.TaskAndSyncState

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskEntity WHERE id = :id")
    fun getTaskAndSyncState(id: String): TaskAndSyncState?

    @Upsert
    fun upsert(taskEntity: TaskEntity)

    @Upsert
    suspend fun upsertTaskSync(taskSyncEntity: TaskSyncEntity)

    @Transaction
    suspend fun upsertSyncStateAndTask(taskEntity: TaskEntity, taskSyncEntity: TaskSyncEntity) {
        upsert(taskEntity)
        upsertTaskSync(taskSyncEntity)
    }

    @Query("DELETE FROM taskEntity WHERE id = :id")
    suspend fun deleteTask(id: String)

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, taskSyncEntity: TaskSyncEntity) {
        deleteTask(id)
        upsertTaskSync(taskSyncEntity)
    }
}