package com.artemissoftware.tasky.authentication.presentation.register

import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType.*

data class RegisterState(
    val nameValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val emailValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val passwordValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val isLoading: Boolean = false
)
