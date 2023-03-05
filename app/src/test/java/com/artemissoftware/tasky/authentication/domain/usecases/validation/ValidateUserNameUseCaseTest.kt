package com.artemissoftware.tasky.authentication.domain.usecases.validation

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateUserNameUseCaseTest {


    private lateinit var validateUserNameUseCase: ValidateUserNameUseCase

    @Before
    fun setUp() {
        validateUserNameUseCase = ValidateUserNameUseCase()
    }

    @Test
    fun `enter valid name get true confirmation`() {

        val name = "Bruce Wayne"
        Assert.assertTrue(validateUserNameUseCase.invoke(name = name))
    }

    @Test
    fun `enter a name with more than 2 words get true confirmation`() {

        val name = "Bruce Wayne Junior"
        val name2 = "Bruce Wayne Senior the third"
        Assert.assertTrue(validateUserNameUseCase.invoke(name = name))
        Assert.assertTrue(validateUserNameUseCase.invoke(name = name2))
    }

    @Test
    fun `enter a name with less characters than mandatory get false confirmation`() {

        val name = "Ana"
        Assert.assertFalse(validateUserNameUseCase.invoke(name = name))
    }

    @Test
    fun `enter a name with more characters than mandatory get false confirmation`() {

        val name = "Bruce Wayne is the batman and no one knows about it. Have to tell this to the joker"
        Assert.assertFalse(validateUserNameUseCase.invoke(name = name))
    }

    @Test
    fun `enter blank name get false confirmation`() {

        val name = "            "
        Assert.assertTrue(validateUserNameUseCase.invoke(name = name))
    }

    @Test
    fun `enter empty name get false confirmation`() {

        val name = ""
        Assert.assertFalse(validateUserNameUseCase.invoke(name = name))
    }


}