package com.artemissoftware.tasky.agenda.data.repositories

import com.artemissoftware.core.data.database.dao.TaskDao
import com.artemissoftware.core.data.database.entities.TaskSyncEntity
import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.domain.models.DataResponse
import com.artemissoftware.core.util.extensions.toEndOfDayEpochMilli
import com.artemissoftware.core.util.extensions.toStartOfDayEpochMilli
import com.artemissoftware.tasky.agenda.data.mappers.toAgendaItem
import com.artemissoftware.tasky.agenda.data.mappers.toDto
import com.artemissoftware.tasky.agenda.data.mappers.toEntity
import com.artemissoftware.tasky.agenda.data.mappers.toTask
import com.artemissoftware.tasky.agenda.data.remote.source.AgendaApiSource
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TaskRepositoryImpl constructor(
    private val taskDao: TaskDao,
    private val agendaApiSource: AgendaApiSource,
    private val alarmScheduler: AlarmScheduler,
) : TaskRepository {

    override suspend fun getTask(id: String): AgendaItem.Task? {
        return taskDao.getTaskAndSyncState(id)?.toAgendaItem()
    }

    override fun getTasks(date: LocalDate): Flow<List<AgendaItem.Task>> {
        return taskDao.getTasks(initialDate = date.toStartOfDayEpochMilli(), endDate = date.toEndOfDayEpochMilli()).map { list ->
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

    override suspend fun upsertTasks(tasks: List<AgendaItem.Task>) {
        val result = tasks.map { it.toEntity() }
        taskDao.upsert(result)
    }

    override suspend fun syncTasksWithRemote(tasks: List<AgendaItem.Task>) {
        if (tasks.isNotEmpty()) {
            val tasksEntities = tasks.map { it.toEntity() }
            val tasksSyncType = tasks.map { TaskSyncEntity(id = it.id, syncType = SyncType.SYNCED) }

            taskDao.upsertSyncStateAndTasks(tasks = tasksEntities, tasksSyncType = tasksSyncType)

            tasks.forEach {
                alarmScheduler.schedule(it)
            }
        }
    }
}
