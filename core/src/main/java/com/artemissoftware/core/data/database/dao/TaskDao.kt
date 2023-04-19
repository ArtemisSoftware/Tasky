package com.artemissoftware.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.data.database.entities.relations.TaskAndSyncState
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE id = :id")
    suspend fun getTaskAndSyncState(id: String): TaskAndSyncState?

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE time >= :initialDate AND time < :endDate")
    fun getTasks(initialDate: Long, endDate: Long): Flow<List<TaskAndSyncState>>

    @Transaction
    @Query("SELECT * FROM taskEntity WHERE time >= :currentTime")
    suspend fun getTasksToSetAlarm(currentTime: Long): List<TaskAndSyncState>

    @Upsert
    fun upsert(taskEntity: TaskEntity)

    @Upsert
    suspend fun upsert(taskEntities: List<TaskEntity>)

    @Upsert
    suspend fun upsertTaskSync(taskSyncEntity: TaskSyncEntity)

    @Upsert
    suspend fun upsertTaskSync(taskSyncEntity: List<TaskSyncEntity>)

    @Transaction
    suspend fun upsertSyncStateAndTask(taskEntity: TaskEntity, taskSyncEntity: TaskSyncEntity) {
        upsert(taskEntity)
        upsertTaskSync(taskSyncEntity)
    }

    @Transaction
    suspend fun upsertSyncStateAndTasks(tasks: List<TaskEntity>, tasksSyncType: List<TaskSyncEntity>) {
        upsert(tasks)
        upsertTaskSync(tasksSyncType)
    }

    @Query("DELETE FROM taskEntity WHERE id = :id")
    suspend fun deleteTask(id: String)

    @Transaction
    suspend fun upsertSyncStateAndDelete(id: String, taskSyncEntity: TaskSyncEntity) {
        deleteTask(id)
        upsertTaskSync(taskSyncEntity)
    }
}
