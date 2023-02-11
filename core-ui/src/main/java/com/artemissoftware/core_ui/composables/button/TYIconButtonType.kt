package com.artemissoftware.core_ui.composables.button

import androidx.annotation.DrawableRes

sealed class TYIconButtonType{
    data class Drawable(@DrawableRes val iconId: Int) : TYIconButtonType()
    data class ImageUrl(val url: String) : TYIconButtonType()
}
