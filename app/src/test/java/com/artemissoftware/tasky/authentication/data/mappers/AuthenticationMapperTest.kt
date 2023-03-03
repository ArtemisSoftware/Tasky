package com.artemissoftware.tasky.authentication.data.mappers

import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.util.FakeData
import org.junit.Assert
import org.junit.Test

class AuthenticationMapperTest {

    @Test
    fun `map LoginResponseDto to User`() {

        val user = User(
            fullName = "Bruce Wayne",
            token = "IamBatman",
            id="DarkKnight"
        )

        Assert.assertEquals(user, FakeData.loginResponseDto.toUser())
    }
}