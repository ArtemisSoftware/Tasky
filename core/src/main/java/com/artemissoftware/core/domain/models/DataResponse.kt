package com.artemissoftware.core.domain.models

import com.artemissoftware.core.data.remote.exceptions.TaskyNetworkException

sealed class DataResponse<T>(val data: T? = null, val exception: TaskyNetworkException? = null) {
    class Success<T>(data: T) : DataResponse<T>(data)
    class Error<T>(exception: TaskyNetworkException) : DataResponse<T>(exception = exception)
}
