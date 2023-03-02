package com.artemissoftware.tasky.authentication.domain.usecases.validation

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `enter valid email get true confirmation`() {

        val email = "email@email.com"
        Assert.assertTrue(validateEmailUseCase.invoke(email = email))
    }

    @Test
    fun `enter invalid email get false confirmation`() {

        val email = "email@email.c"
        val email2 = "emailemail.com"
        val email3 = "email"

        Assert.assertFalse(validateEmailUseCase.invoke(email = email))
        Assert.assertFalse(validateEmailUseCase.invoke(email = email2))
        Assert.assertFalse(validateEmailUseCase.invoke(email = email3))
    }

    @Test
    fun `enter blank email get false confirmation`() {

        val email = "   "
        Assert.assertFalse(validateEmailUseCase.invoke(email = email))
    }

    @Test
    fun `enter empty email get false confirmation`() {

        val email = ""
        Assert.assertFalse(validateEmailUseCase.invoke(email = email))
    }
}