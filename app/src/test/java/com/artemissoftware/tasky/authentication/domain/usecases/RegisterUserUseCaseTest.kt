package com.artemissoftware.tasky.authentication.domain.usecases

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.domain.models.api.ApiNetworkResponse
import com.artemissoftware.core.domain.repositories.UserStoreRepository
import com.artemissoftware.tasky.authentication.domain.repositories.AuthenticationRepository
import com.artemissoftware.tasky.util.BaseUseCaseTest
import com.artemissoftware.tasky.util.FakeData
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
        authenticationRepository = mock()
        registerUserUseCase = RegisterUserUseCase(authenticationRepository = authenticationRepository)
    }

    @Test
    fun `Register user with success`() = runTest {

        val email = "batman@waynetech.com"
        val password = "Iambatman123"
        val fullName = "Bruce Wayne"

        whenever(authenticationRepository.registerUser(email = email, password = password, fullName = fullName)).thenReturn(
            ApiNetworkResponse.Success(true)
        )

        val emissions = registerUserUseCase(email = email, password = password, fullName = fullName).toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Success)

        verify(authenticationRepository, times(1)).registerUser(email = email, password = password, fullName = fullName)
    }


    @Test
    fun `Register user with failure`() = runTest {

        val email = "batman@waynetech.com"
        val password = "Iambatman123"
        val fullName = "Bruce Wayne"

        whenever(authenticationRepository.registerUser(email = email, password = password, fullName = fullName)).thenReturn(
            ApiNetworkResponse.Error(exception = TaskyNetworkException())
        )

        val emissions = registerUserUseCase(email = email, password = password, fullName = fullName).toList()

        assert(emissions[0] is Resource.Loading)
        assert(emissions[1] is Resource.Error)

        verify(authenticationRepository, times(1)).registerUser(email = email, password = password, fullName = fullName)
    }

}