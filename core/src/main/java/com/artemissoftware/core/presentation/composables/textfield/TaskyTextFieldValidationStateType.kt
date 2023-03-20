package com.artemissoftware.core.presentation.composables.textfield

enum class TaskyTextFieldValidationStateType {

    VALID,
    INVALID,
    NOT_VALIDATED;

    companion object {
        fun getStateType(valid: Boolean) = if(valid) VALID else INVALID
    }

}