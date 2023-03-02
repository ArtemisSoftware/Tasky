package com.artemissoftware.core.presentation.composables.button

import androidx.annotation.DrawableRes

sealed class TaskyIconButtonType{
    data class Drawable(@DrawableRes val iconId: Int) : TaskyIconButtonType()
    data class ImageUrl(val url: String) : TaskyIconButtonType()
}
