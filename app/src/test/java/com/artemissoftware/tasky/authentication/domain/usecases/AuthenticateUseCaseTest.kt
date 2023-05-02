package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.authentication.data.repositories.FakeAuthenticationRepository
import com.artemissoftware.tasky.util.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticateUseCaseTest: BaseUseCaseTest() {

    private lateinit var authenticateUseCase: AuthenticateUseCase

    private lateinit var authenticationRepository: FakeAuthenticationRepository

    @Before
    fun setUp() {

        authenticationRepository = FakeAuthenticationRepository()
        authenticateUseCase = AuthenticateUseCase(authenticationRepository = authenticationRepository)
    }

    @Test
    fun `Authenticate user with success`() = runTest {

        val result = authenticateUseCase()

        assert(result is Resource.Success)
    }

    @Test
    fun `Authenticate user with failure and error message from backend`() = runTest {

        authenticationRepository.errorWithBackendMessage = true

        val result = authenticateUseCase()

        assert(result is Resource.Error)
        assert(result.exception is ValidationException.DataError)
        assertEquals(UiText.DynamicString(FakeAuthenticationRepository.BACKEND_ERROR), (result.exception as ValidationException.DataError).uiText)
    }


    @Test
    fun `Authenticate user with failure with custom exception`() = runTest {

        authenticationRepository.returnNetworkError = true

        val result = authenticateUseCase()

        assert(result is Resource.Error)
        assert(result.exception is AuthenticationException.UserNotAuthenticated)
    }

}