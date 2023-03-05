package com.artemissoftware.core.data.local.models
import kotlinx.serialization.Serializable


@Serializable
data class UserStore(
    val fullName: String,
    val token: String,
    val id: String
)
