package com.artemissoftware.tasky.data.local.models
import kotlinx.serialization.Serializable


@Serializable
data class UserSettings(
    val name: String,
    val token: String
)
