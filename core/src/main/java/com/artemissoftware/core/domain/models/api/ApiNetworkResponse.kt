package com.artemissoftware.core.domain.models.api

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException

sealed class ApiNetworkResponse<T>(val data: T? = null, val exception: TaskyNetworkException? = null) {
    class Success<T>(data: T) : ApiNetworkResponse<T>(data)
    class Error<T>(exception: TaskyNetworkException) : ApiNetworkResponse<T>(exception = exception)
}