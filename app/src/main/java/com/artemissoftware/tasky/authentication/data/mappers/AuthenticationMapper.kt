package com.artemissoftware.tasky.authentication.data.mappers

import com.artemissoftware.core.domain.models.authentication.User
import com.artemissoftware.tasky.authentication.data.remote.dto.LoginResponseDto

fun LoginResponseDto.toUser(): User {
    return User(
        fullName = fullName,
        token = token,
        id = userId
    )
}