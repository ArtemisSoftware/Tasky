package com.artemissoftware.tasky.authentication.presentation.login

import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.tasky.authentication.presentation.register.RegisterEvents

sealed class LoginEvents: TaskyEvents() {
    data class ValidateEmail(val email: String): LoginEvents()
    data class ValidatePassword(val password: String): LoginEvents()
    object SignUp: LoginEvents()
    object Login: LoginEvents()
}
