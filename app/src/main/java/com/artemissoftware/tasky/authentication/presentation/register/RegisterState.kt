package com.artemissoftware.tasky.authentication.presentation.register

import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType.*

data class RegisterState(
    val name: String = "",
    val nameValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val email: String = "",
    val emailValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val password: String = "",
    val passwordValidationStateType: TaskyTextFieldValidationStateType = NOT_VALIDATED,
    val isLoading: Boolean = false
){

    fun allRegisterFieldsValid(): Boolean {

        return (emailValidationStateType == VALID
                &&
                passwordValidationStateType == VALID
                &&
                nameValidationStateType == VALID)
    }
}
