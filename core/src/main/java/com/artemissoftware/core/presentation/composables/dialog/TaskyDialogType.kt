package com.artemissoftware.core.presentation.composables.dialog

import androidx.annotation.RawRes
import com.artemissoftware.core.R
import com.artemissoftware.core.util.UiText

sealed class TaskyDialogType(
    val title: UiText,
    val description: UiText,
    @RawRes val lottieId: Int? = null,
    val dialogOptions: TaskyDialogOptions
){
    class Success(
        title: UiText,
        description: UiText,
        dialogOptions: TaskyDialogOptions
    ) : TaskyDialogType(title = title, description = description, lottieId = R.raw.success_lottie, dialogOptions = dialogOptions)

    class Error(
        title: UiText,
        description: UiText,
        dialogOptions: TaskyDialogOptions
    ) : TaskyDialogType(title = title, description = description, lottieId = R.raw.error_lottie, dialogOptions = dialogOptions)

    class Info(
        title: UiText,
        description: UiText,
        dialogOptions: TaskyDialogOptions
    ) : TaskyDialogType(title = title, description = description, lottieId = R.raw.info_lottie, dialogOptions = dialogOptions)
}
