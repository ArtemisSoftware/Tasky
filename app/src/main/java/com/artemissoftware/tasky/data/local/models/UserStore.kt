package com.artemissoftware.tasky.data.local.models
import kotlinx.serialization.Serializable


@Serializable
data class UserStore(
    val name: String,
    val token: String
)
