package com.artemissoftware.core.data.local.models
import kotlinx.serialization.Serializable


@Serializable
data class UserStore(
    val fullName: String? = null,
    val token: String? = null,
    val id: String? = null
)
