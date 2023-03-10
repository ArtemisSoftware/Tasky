package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.util.BaseUseCaseTest
import com.artemissoftware.tasky.util.FakeData
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginUseCaseTest: BaseUseCaseTest() {


    private lateinit var loginUseCase: LoginUseCase

    private lateinit var userStoreRepository: UserStoreRepository
    private lateinit var authenticationRepository: AuthenticationRepository

    @Before
    fun setUp() {
        userStoreRepository = mock()
        authenticationRepository = mock()
        loginUseCase = LoginUseCase(userStoreRepository = userStoreRepository, authenticationRepository = authenticationRepository)
    }

    @Test
    fun `Login user with success`() = runTest {

        val email = "batman@waynetech.com"
        val password = "Iambatman123"

        whenever(authenticationRepository.loginUser(email = email, password = password)).thenReturn(
            ApiNetworkResponse.Success(data = FakeData.user)
        )

        val emissions = loginUseCase(email = email, password = password).toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Success)

        verify(authenticationRepository, times(1)).loginUser(email = email, password = password)
        verify(userStoreRepository, times(1)).saveUser(user = FakeData.user)
    }


    @Test
    fun `Login user with failure`() = runTest {

        val email = "batman@waynetech.com"
        val password = "Iambatman123"

        whenever(authenticationRepository.loginUser(email = email, password = password)).thenReturn(
            ApiNetworkResponse.Error(exception = TaskyNetworkException())
        )

        val emissions = loginUseCase(email = email, password = password).toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Error)

        verify(authenticationRepository, times(1)).loginUser(email = email, password = password)
        verify(userStoreRepository, times(0)).saveUser(user = FakeData.user)
    }

}