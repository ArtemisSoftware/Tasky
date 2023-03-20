package com.artemissoftware.tasky.agenda.domain.usecase.task

import com.artemissoftware.tasky.util.BaseUseCaseTest
import com.artemissoftware.tasky.util.FakeData
import com.artemissoftware.tasky.util.fakes.FakeTaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetTaskUseCaseTest : BaseUseCaseTest() {

    private lateinit var getTaskUseCase: GetTaskUseCase

    private lateinit var taskRepository: FakeTaskRepository

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        getTaskUseCase = GetTaskUseCase(taskRepository = taskRepository)
    }

    @Test
    fun `Get task and get a task`() = runTest {
        val result = getTaskUseCase("taskId")

        assertEquals(result, FakeData.task)
    }

    @Test
    fun `Get task and get no task`() = runTest {
        val result = getTaskUseCase("jokerId")

        assertNull(result)
    }
}
