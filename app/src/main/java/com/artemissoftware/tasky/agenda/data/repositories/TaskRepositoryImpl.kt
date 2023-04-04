package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toDto
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class TaskRepositoryImpl constructor(
    private val taskDao: TaskDao,
    private val agendaApiSource: AgendaApiSource,
) : TaskRepository {

    override suspend fun getTask(id: String): AgendaItem.Task? {
        return taskDao.getTaskAndSyncState(id)?.toAgendaItem()
    }

    override fun getTasks(date: LocalDate): Flow<List<AgendaItem.Task>> {
        val initialDate = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endDate = date.atStartOfDay().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        return taskDao.getTasks(initialDate = initialDate, endDate = endDate).map { list ->
            list.map { item -> item.toAgendaItem() }
        }
    }

    override suspend fun saveTaskAndSync(task: AgendaItem.Task): DataResponse<Unit> {
        val syncType = if (task.syncState == SyncType.SYNCED) SyncType.UPDATE else task.syncState
        taskDao.upsertSyncStateAndTask(task.toEntity(), TaskSyncEntity(id = task.id, syncType = syncType))

        return try {
            when (syncType) {
                SyncType.CREATE -> {
                    agendaApiSource.createTask(task.toDto())
                }
                SyncType.UPDATE -> {
                    agendaApiSource.updateTask(task.toDto())
                }
                else -> Unit
            }
            taskDao.upsertTaskSync(TaskSyncEntity(id = task.id, syncType = SyncType.SYNCED))
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }

    override suspend fun deleteTaskAndSync(id: String): DataResponse<Unit> {
        taskDao.upsertSyncStateAndDelete(id = id, TaskSyncEntity(id = id, syncType = SyncType.DELETE))

        return try {
            agendaApiSource.deleteTask(taskId = id)
            taskDao.upsertTaskSync(TaskSyncEntity(id = id, syncType = SyncType.SYNCED))
            DataResponse.Success(Unit)
        } catch (ex: TaskyNetworkException) {
            DataResponse.Error(exception = ex)
        }
    }
}
