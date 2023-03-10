package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.tasky.authentication.data.repositories.FakeAuthenticationRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.util.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUserUseCaseTest: BaseUseCaseTest() {

    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setUp() {
        authenticationRepository = FakeAuthenticationRepository()
        registerUserUseCase = RegisterUserUseCase(authenticationRepository = authenticationRepository)
    }

    @Test
    fun `Register user with success`() = runTest {

        val email = "batman@waynetech.com"
        val password = "Iambatman123"
        val fullName = "Bruce Wayne"

        val emissions = registerUserUseCase(email = email, password = password, fullName = fullName)

        assert(emissions is Resource.Success)
    }


    @Test
    fun `Register user with failure`() = runTest {

        (authenticationRepository as FakeAuthenticationRepository).returnNetworkError = true
        val email = "batman@waynetech.com"
        val password = "Iambatman123"
        val fullName = "Bruce Wayne"

        val emissions = registerUserUseCase(email = email, password = password, fullName = fullName)

        assert(emissions is Resource.Error)
    }

}