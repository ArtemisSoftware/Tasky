package com.artemissoftware.core.data.remote.exceptions

import com.artemissoftware.core.util.UiText

data class TaskyNetworkException (
    val code: Int,
    val description: UiText
): RuntimeException(){

    constructor(taskyNetworkError: TaskyNetworkError = TaskyNetworkError.GenericApiError) : this(code = taskyNetworkError.code, description = UiText.StringResource(taskyNetworkError.description))
    constructor(code: Int, description: Int) : this(code = code, description = UiText.StringResource(description))

    constructor(code: Int, description: String) : this(code = code, description = UiText.DynamicString(description))
}