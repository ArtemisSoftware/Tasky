package com.artemissoftware.tasky.agenda.domain.usecase.reminder

import com.artemissoftware.core.data.repositories.FakeUserStoreRepository
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.tasky.agenda.data.FakeAgendaRepository
import com.artemissoftware.tasky.agenda.domain.repositories.AgendaRepository
import com.artemissoftware.tasky.authentication.data.repositories.FakeAuthenticationRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.authentication.domain.usecases.LoginUseCase
import com.artemissoftware.tasky.util.BaseUseCaseTest
import com.artemissoftware.tasky.util.FakeData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetReminderUseCaseTest: BaseUseCaseTest() {

    private lateinit var getReminderUseCase: GetReminderUseCase

    private lateinit var agendaRepository: FakeAgendaRepository

    @Before
    fun setUp() {
        agendaRepository = FakeAgendaRepository()
        getReminderUseCase = GetReminderUseCase(agendaRepository = agendaRepository)
    }

    @Test
    fun `Get user with success`() = runTest {

        val id = FakeData.reminder.id
        val result = getReminderUseCase(id = id)

        assertEquals(result, FakeData.reminder)
    }

}