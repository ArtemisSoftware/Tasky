package com.artemissoftware.tasky.authentication.domain.usecases.validation

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

    @Before
    fun setUp() {
        validatePasswordUseCase = ValidatePasswordUseCase()
    }

    @Test
    fun `enter valid password get true confirmation`() {

        val password = "Password1"
        Assert.assertTrue(validatePasswordUseCase.invoke(password = password))
    }

    @Test
    fun `enter invalid password get false confirmation`() {

        val password = "Passw.ord1"
        val password2 = "password1"
        val password3 = "AAAAAAAAAAA"
        val password4 = "bbbffffdddss"
        Assert.assertFalse(validatePasswordUseCase.invoke(password = password))
        Assert.assertFalse(validatePasswordUseCase.invoke(password = password2))
        Assert.assertFalse(validatePasswordUseCase.invoke(password = password3))
        Assert.assertFalse(validatePasswordUseCase.invoke(password = password4))
    }

    @Test
    fun `enter a password with less characters than mandatory get false confirmation`() {

        val name = "Pass"
        Assert.assertFalse(validatePasswordUseCase.invoke(password = name))
    }

    @Test
    fun `enter blank password get false confirmation`() {

        val name = "          "
        Assert.assertFalse(validatePasswordUseCase.invoke(password = name))
    }

    @Test
    fun `enter empty password get false confirmation`() {

        val name = ""
        Assert.assertFalse(validatePasswordUseCase.invoke(password = name))
    }

    @Test
    fun `enter password with spaces get false confirmation`() {

        val name = "     d  1  A   "
        Assert.assertFalse(validatePasswordUseCase.invoke(password = name))
    }
}