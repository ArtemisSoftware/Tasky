package com.artemissoftware.core.domain.usecase

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUserNameInitialsUseCaseTest {

    private lateinit var getUserNameInitialsUseCase: GetUserNameInitialsUseCase

    @Before
    fun setUp() {
        getUserNameInitialsUseCase = GetUserNameInitialsUseCase()
    }

    @Test
    fun `enter name with 2 words get first letter from first and second word`() {

        val name = "Bruce Wayne"
        val initials = "BW"
        Assert.assertEquals(initials, getUserNameInitialsUseCase(name))
    }

    @Test
    fun `enter name with 1 words get first letter from first word`() {

        val name = "Bruce"
        val initials = "B"
        Assert.assertEquals(initials, getUserNameInitialsUseCase(name))
    }
}