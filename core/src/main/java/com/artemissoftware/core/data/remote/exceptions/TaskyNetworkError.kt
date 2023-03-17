package com.artemissoftware.core.data.remote.exceptions

import com.artemissoftware.core.R

sealed class TaskyNetworkError(
    val code: Int,
    val description: Int
) {
    object UnknownHost : TaskyNetworkError(code = 1, description = R.string.unknown_host)
    object GenericApiError : TaskyNetworkError(code = 2, description = R.string.an_error_occurred)
    object Cancellation : TaskyNetworkError(code = 3, description = R.string.coroutine_cancelled)

}