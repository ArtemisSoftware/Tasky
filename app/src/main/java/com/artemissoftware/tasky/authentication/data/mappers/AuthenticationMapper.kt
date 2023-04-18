package com.artemissoftware.tasky.authentication.data.mappers

import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.core.data.remote.dto.authentication.LoginResponseDto

fun LoginResponseDto.toUser(): User {
    return User(
        fullName = fullName,
        token = token,
        id = userId
    )
}