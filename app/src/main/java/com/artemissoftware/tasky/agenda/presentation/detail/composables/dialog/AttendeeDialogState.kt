package com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog

import android.os.Parcelable
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.util.UiText
import kotlinx.parcelize.Parcelize


@Parcelize
data class AttendeeDialogState(
    val emailValidationStateType: TaskyTextFieldValidationStateType = TaskyTextFieldValidationStateType.NOT_VALIDATED,
    val errorMessage: UiText? = null,
    val showDialog: Boolean = false,
    val email: String = "",
) : Parcelable
