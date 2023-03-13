package com.artemissoftware.core.domain

import com.artemissoftware.core.util.UiText

sealed class ValidationException{

    data class DataError(val uiText: UiText) : ValidationException()
}
