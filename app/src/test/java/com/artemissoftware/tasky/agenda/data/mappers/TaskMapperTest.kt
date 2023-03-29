package com.artemissoftware.tasky.agenda.data.mappers

import com.artemissoftware.core.data.database.entities.TaskEntity
import com.artemissoftware.core.domain.SyncType
import com.artemissoftware.core.util.extensions.toLong
import com.artemissoftware.tasky.agenda.data.remote.dto.TaskDto
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class TaskMapperTest {

    @Test
    fun `map TaskDto to Entity`() {
        val remindAt = LocalDateTime.now().minusMinutes(10).toLong()
        val time = LocalDateTime.now().minusMinutes(20).toLong()

        val taskDto = TaskDto(
            description = "Get rid of Batman",
            id = "11002D",
            isDone = false,
            remindAt = remindAt,
            time = time,
            title = "Just for laughs",
        )

        val taskEntity = TaskEntity(
            description = "Get rid of Batman",
            id = "11002D",
            isDone = false,
            remindAt = remindAt,
            time = time,
            title = "Just for laughs",
        )

        Assert.assertEquals(taskEntity, taskDto.toEntity())
    }

    @Test
    fun `map Task to AgendaItem TaskDto`() {
        val remindAt = LocalDateTime.now().minusMinutes(0)
        val time = LocalDateTime.now().minusMinutes(20)

        val task = AgendaItem.Task(
            description = "Get rid of Batman",
            id = "11002D",
            isDone = false,
            remindAt = remindAt,
            time = time,
            title = "Just for laughs",
            syncState = SyncType.SYNCED,
        )

        val taskDto = TaskDto(
            description = "Get rid of Batman",
            id = "11002D",
            isDone = false,
            remindAt = remindAt.toLong(),
            time = time.toLong(),
            title = "Just for laughs",
        )

        Assert.assertEquals(taskDto, task.toDto())
    }

    @Test
    fun `map Task to TaskEntityo`() {
        val remindAt = LocalDateTime.now().minusMinutes(0)
        val time = LocalDateTime.now().minusMinutes(20)

        val task = AgendaItem.Task(
            description = "Get rid of Batman",
            id = "11002D",
            isDone = false,
            remindAt = remindAt,
            time = time,
            title = "Just for laughs",
            syncState = SyncType.SYNCED,
        )

        val taskEntity = TaskEntity(
            description = "Get rid of Batman",
            id = "11002D",
            isDone = false,
            remindAt = remindAt.toLong(),
            time = time.toLong(),
            title = "Just for laughs",
        )

        Assert.assertEquals(taskEntity, task.toEntity())
    }
}
