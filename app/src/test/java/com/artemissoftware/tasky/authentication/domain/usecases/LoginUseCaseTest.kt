package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.data.repositories.FakeUserStoreRepository
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.tasky.authentication.data.repositories.FakeAuthenticationRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.util.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest: BaseUseCaseTest() {

    private lateinit var loginUseCase: LoginUseCase

    private lateinit var userStoreRepository: UserStoreRepository
    private lateinit var authenticationRepository: AuthenticationRepository

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
    fun `Login user with failure`() = runTest {

        (authenticationRepository as FakeAuthenticationRepository).returnNetworkError = true
        val email = "batman@waynetech.com"
        val password = "Iambatman123"

        val result = loginUseCase(email = email, password = password)

        assert(result is Resource.Error)
    }

}