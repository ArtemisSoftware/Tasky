package com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog

import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType

data class AttendeeDialogState(
    val emailValidationStateType: TaskyTextFieldValidationStateType = TaskyTextFieldValidationStateType.NOT_VALIDATED,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
)
