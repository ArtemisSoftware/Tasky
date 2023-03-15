package com.artemissoftware.core.presentation.composables.dialog

import com.artemissoftware.core.util.UiText

sealed class TaskyDialogOptions(
    val optionText: UiText,
    val confirmationOption: () -> Unit = {},
) {
    data class SingleOption(
        val confirmationText: UiText,
        val confirmation: () -> Unit = {}
    ) : TaskyDialogOptions(
        optionText = confirmationText,
        confirmationOption = confirmation
    )

    data class DoubleOption(
        val confirmationText: UiText,
        val confirmation: () -> Unit = {},
        val cancelText: UiText,
        val cancel: () -> Unit = {},
    ) : TaskyDialogOptions(
        optionText = confirmationText,
        confirmationOption = confirmation
    )
}