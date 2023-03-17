package com.artemissoftware.tasky.authentication.presentation.login

import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType.*

data class LoginState(
    val email: String = "",
    val emailValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val password: String = "",
    val passwordValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val isLoading: Boolean = false,
    val scaffoldState: TaskyScaffoldState = TaskyScaffoldState()
){

    fun allLoginFieldsValid(): Boolean {

        return (emailValidationStateType == VALID
                &&
                passwordValidationStateType == VALID)
    }
}

