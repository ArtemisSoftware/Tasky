package com.artemissoftware.core_ui.composables.dialog

import androidx.annotation.RawRes

sealed class TYDialogType(
    val title: String,
    val description: String,
    @RawRes val lottieId: Int? = null,
    val dialogOptions: TYDialogOptions
){
    class Success(
        title: String,
        description: String,
        @RawRes lottieId: Int? = null,
        dialogOptions: TYDialogOptions
    ) : TYDialogType(title = title, description = description, lottieId = lottieId, dialogOptions = dialogOptions)

    class Error(
        title: String,
        description: String,
        @RawRes lottieId: Int? = null,
        dialogOptions: TYDialogOptions
    ) : TYDialogType(title = title, description = description, lottieId = lottieId, dialogOptions = dialogOptions)

    class Info(
        title: String,
        description: String,
        @RawRes lottieId: Int? = null,
        dialogOptions: TYDialogOptions
    ) : TYDialogType(title = title, description = description, lottieId = lottieId, dialogOptions = dialogOptions)
}
