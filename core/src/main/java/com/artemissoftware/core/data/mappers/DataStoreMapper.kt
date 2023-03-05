package com.artemissoftware.core.data.mappers

import com.artemissoftware.core.data.local.models.UserStore
import com.artemissoftware.core.domain.models.authentication.User

fun UserStore.toUser() : User{
    return User(
        fullName = fullName ?: "",
        token = token,
        id = id ?: ""
    )
}

fun User.toUserStore() : UserStore{
    return UserStore(
        fullName = fullName,
        token = token,
        id = id
    )
}