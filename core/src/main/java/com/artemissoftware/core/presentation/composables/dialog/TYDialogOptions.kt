package com.artemissoftware.core_ui.composables.dialog

import androidx.annotation.StringRes

data class TYDialogOptions(
    @StringRes val confirmationTextId: Int,
    val confirmation: () -> Unit = {},
    @StringRes val cancelTextId: Int? = null,
    val cancel: () -> Unit = {},
) {
    fun getOptionsType(): TYDialogButtonType{

        return when{

            (cancelTextId != null) ->{
                TYDialogButtonType.DOUBLE_OPTION
            }
            else ->{
                TYDialogButtonType.SINGLE_OPTION
            }

        }
    }
}