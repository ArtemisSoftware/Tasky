package com.artemissoftware.core.data.remote.exceptions

data class TaskyNetworkException (
    val code: Int = NetworkErrors.GENERIC_API_ERROR.first,
    override val message: String? = NetworkErrors.GENERIC_API_ERROR.second
): RuntimeException()