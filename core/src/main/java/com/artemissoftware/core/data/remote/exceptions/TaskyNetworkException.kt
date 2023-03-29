package com.artemissoftware.core.data.remote.exceptions

import com.artemissoftware.core.util.UiText

data class TaskyNetworkException (
    val code: Int = TaskyNetworkError.GenericApiError.code,
    val description: UiText? = null
): RuntimeException(){

    constructor(taskyNetworkError: TaskyNetworkError) : this(code = taskyNetworkError.code, description = UiText.StringResource(taskyNetworkError.description))

    constructor(code: Int, description: String) : this(code = code, description = UiText.DynamicString(description))
}