package com.artemissoftware.tasky.authentication.presentation.login

import com.artemissoftware.core.presentation.composables.TaskyScaffoldState
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType.NOT_VALIDATED
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType.VALID

data class LoginState(
    val email: String = "",
    val emailValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val password: String = "",
    val passwordValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val isLoading: Boolean = false,
    val scaffoldState: TaskyScaffoldState = TaskyScaffoldState(),
) {

    fun allLoginFieldsValid(): Boolean {
        return (
            emailValidationStateType == VALID &&
                passwordValidationStateType == VALID
            )
    }
}
