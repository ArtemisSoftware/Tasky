package com.artemissoftware.core.domain

import com.artemissoftware.core.util.UiText

sealed class AgendaException : ValidationException() {

    object AttendeeError : AgendaException()
    object AttendeeDoesNotExist : AgendaException()
    data class AttendeeCannotAddItself(val uiText: UiText) : AgendaException()
    data class NotValidPictures(val uiText: UiText) : AgendaException()

    object LogOutError : AgendaException()
}
