package com.artemissoftware.core.domain.models

import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.util.UiText

sealed class Resource<T>(val data: T? = null, val exception: ValidationException? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: ValidationException, data: T? = null) : Resource<T>(data, exception)
    class Loading<T>(data: T? = null) : Resource<T>(data)

    class NotAuthenticated<T>: Resource<T>()
}