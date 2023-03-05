package com.artemissoftware.core.domain.models.authentication

data class User(
    val fullName: String,
    val token: String? = null,
    val id: String
)
