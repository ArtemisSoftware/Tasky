package com.artemissoftware.core_ui.composables.dialog

import androidx.annotation.RawRes
import com.artemissoftware.core_ui.R

sealed class TYDialogType(
    val title: String,
    val description: String,
    @RawRes val lottieId: Int? = null,
    val dialogOptions: TYDialogOptions
){
    class Success(
        title: String,
        description: String,
        dialogOptions: TYDialogOptions
    ) : TYDialogType(title = title, description = description, lottieId = R.raw.success_lottie, dialogOptions = dialogOptions)

    class Error(
        title: String,
        description: String,
        dialogOptions: TYDialogOptions
    ) : TYDialogType(title = title, description = description, lottieId = R.raw.error_lottie, dialogOptions = dialogOptions)

    class Info(
        title: String,
        description: String,
        dialogOptions: TYDialogOptions
    ) : TYDialogType(title = title, description = description, lottieId = R.raw.info_lottie, dialogOptions = dialogOptions)
}
