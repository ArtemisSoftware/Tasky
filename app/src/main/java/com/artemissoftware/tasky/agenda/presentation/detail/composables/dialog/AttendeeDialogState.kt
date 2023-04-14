package com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog

import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.util.UiText

data class AttendeeDialogState(
    val emailValidationStateType: TaskyTextFieldValidationStateType = TaskyTextFieldValidationStateType.NOT_VALIDATED,
    val errorMessage: UiText? = null,
    val showDialog: Boolean = false,
    val email: String = "",
)
