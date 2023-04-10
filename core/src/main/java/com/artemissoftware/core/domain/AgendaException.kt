package com.artemissoftware.core.domain

sealed class AgendaException : ValidationException() {

    object AttendeeError : AgendaException()
    object AttendeeDoesNotExist : AgendaException()
}
