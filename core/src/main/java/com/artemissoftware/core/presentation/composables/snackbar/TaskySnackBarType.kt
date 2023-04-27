package com.artemissoftware.core.presentation.composables.snackbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.artemissoftware.core.presentation.theme.Light
import com.artemissoftware.core.util.UiText

sealed class TaskySnackBarType(
    val text: UiText,
    val imageVector: ImageVector,
    val color: Color,
) {

    class Info(
        text: UiText,
        imageVector: ImageVector,
    ) : TaskySnackBarType(text = text, imageVector = imageVector, color = Light)
}
