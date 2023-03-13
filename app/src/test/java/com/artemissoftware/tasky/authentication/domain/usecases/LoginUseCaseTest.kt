package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.data.repositories.FakeUserStoreRepository
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
class LoginUseCaseTest: BaseUseCaseTest() {

    private lateinit var loginUseCase: LoginUseCase

    private lateinit var userStoreRepository: FakeUserStoreRepository
    private lateinit var authenticationRepository: FakeAuthenticationRepository

    @Before
    fun setUp() {

        userStoreRepository = FakeUserStoreRepository()
        authenticationRepository = FakeAuthenticationRepository()
        loginUseCase = LoginUseCase(userStoreRepository = userStoreRepository, authenticationRepository = authenticationRepository)
    }

    @Test
    fun `Login user with success`() = runTest {

        val email = "batman@waynetech.com"
        val password = "Iambatman123"

        val result = loginUseCase(email = email, password = password)

        assert(result is Resource.Success)
    }


    @Test
    fun `Login user with failure and error message from backend`() = runTest {

        authenticationRepository.errorWithBackendMessage = true
        val email = "batman@waynetech.com"
        val password = "Iambatman123"

        val result = loginUseCase(email = email, password = password)

        assert(result is Resource.Error)
        assert(result.exception is ValidationException.DataError)
        assertEquals(UiText.DynamicString(FakeAuthenticationRepository.BACKEND_ERROR), (result.exception as ValidationException.DataError).uiText)
    }


    @Test
    fun `Login user with failure with custom exception`() = runTest {

        authenticationRepository.returnNetworkError = true
        val email = "batman@waynetech.com"
        val password = "Iambatman123"

        val result = loginUseCase(email = email, password = password)

        assert(result is Resource.Error)
        assert(result.exception is AuthenticationException.LoginError)
    }
}