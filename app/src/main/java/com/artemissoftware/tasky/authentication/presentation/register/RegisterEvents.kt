package com.artemissoftware.tasky.authentication.presentation.register

import com.artemissoftware.core.presentation.events.TaskyEvents

sealed class RegisterEvents: TaskyEvents() {
    data class ValidateEmail(val email: String): RegisterEvents()
    data class ValidateName(val name: String): RegisterEvents()
    data class ValidatePassword(val password: String): RegisterEvents()
    object Register: RegisterEvents()
    object PopBackStack : RegisterEvents()
}
