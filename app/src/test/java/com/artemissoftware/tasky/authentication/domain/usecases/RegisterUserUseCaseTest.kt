package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.util.UiText
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
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUserUseCaseTest: BaseUseCaseTest() {

    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var authenticationRepository: FakeAuthenticationRepository

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

        val result = registerUserUseCase(email = email, password = password, fullName = fullName)

        assert(result is Resource.Success)
    }


    @Test
    fun `Register user with failure and error message from backend`() = runTest {

        authenticationRepository.errorWithBackendMessage = true
        val email = "batman@waynetech.com"
        val password = "Iambatman123"
        val fullName = "Bruce Wayne"

        val result = registerUserUseCase(email = email, password = password, fullName = fullName)

        assert(result is Resource.Error)
        assert(result.exception is ValidationException.DataError)
        assertEquals(UiText.DynamicString(FakeAuthenticationRepository.BACKEND_ERROR), (result.exception as ValidationException.DataError).uiText)
    }

    @Test
    fun `Register user with failure with custom exception`() = runTest {

        authenticationRepository.returnNetworkError = true
        val email = "batman@waynetech.com"
        val password = "Iambatman123"
        val fullName = "Bruce Wayne"

        val result = registerUserUseCase(email = email, password = password, fullName = fullName)

        assert(result is Resource.Error)
        assert(result.exception is AuthenticationException.RegisterError)
    }
}