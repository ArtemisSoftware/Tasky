package com.artemissoftware.core.presentation.composables.dialog

import androidx.annotation.StringRes

sealed class TaskyDialogOptions(
    @StringRes val optionText: Int,
    val confirmationOption: () -> Unit = {},
) {
    data class SingleOption(
        @StringRes val confirmationText: Int,
        val confirmation: () -> Unit = {}
    ) : TaskyDialogOptions(
        optionText = confirmationText,
        confirmationOption = confirmation
    )

    data class DoubleOption(
        @StringRes val confirmationText: Int,
        val confirmation: () -> Unit = {},
        @StringRes val cancelText: Int,
        val cancel: () -> Unit = {},
    ) : TaskyDialogOptions(
        optionText = confirmationText,
        confirmationOption = confirmation
    )
}