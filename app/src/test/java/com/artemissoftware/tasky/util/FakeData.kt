package com.artemissoftware.tasky.util

import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto

object FakeData {

    val loginResponseDto = LoginResponseDto(
        fullName = "Bruce Wayne",
        token = "IamBatman",
        userId="DarkKnight"
    )

    val user = User(
        fullName = "Bruce Wayne",
        token = "IamBatman",
        id = "DarkKnight"
    )
}