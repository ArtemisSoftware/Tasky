package com.artemissoftware.core.presentation.mappers

import com.artemissoftware.core.R
import com.artemissoftware.core.domain.AgendaException
import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.util.UiText

fun ValidationException.toUiText(): UiText {
    return when (this) {
        AuthenticationException.LoginError -> {
            UiText.StringResource(R.string.error_occurred_during_login)
        }
        AuthenticationException.RegisterError -> {
            UiText.StringResource(R.string.error_occurred_during_user_registration)
        }
        AuthenticationException.UserNotAuthenticated -> {
            UiText.StringResource(R.string.user_not_currently_authenticated)
        }
        is ValidationException.DataError -> {
            this.uiText
        }
        AgendaException.AttendeeDoesNotExist -> {
            UiText.StringResource(R.string.attendee_not_exist)
        }
        AgendaException.AttendeeError -> {
            UiText.StringResource(R.string.error_occurred_during_attendee_search)
        }
        AgendaException.LogOutError -> {
            UiText.StringResource(R.string.error_occurred_during_user_logout)
        }
    }
}
