package com.artemissoftware.core_ui.composables.dialog

import androidx.annotation.RawRes
import com.artemissoftware.core.R
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions

sealed class TaskyDialogType(
    val title: String,
    val description: String,
    @RawRes val lottieId: Int? = null,
    val dialogOptions: TaskyDialogOptions
){
    class Success(
        title: String,
        description: String,
        dialogOptions: TaskyDialogOptions
    ) : TaskyDialogType(title = title, description = description, lottieId = R.raw.success_lottie, dialogOptions = dialogOptions)

    class Error(
        title: String,
        description: String,
        dialogOptions: TaskyDialogOptions
    ) : TaskyDialogType(title = title, description = description, lottieId = R.raw.error_lottie, dialogOptions = dialogOptions)

    class Info(
        title: String,
        description: String,
        dialogOptions: TaskyDialogOptions
    ) : TaskyDialogType(title = title, description = description, lottieId = R.raw.info_lottie, dialogOptions = dialogOptions)
}
