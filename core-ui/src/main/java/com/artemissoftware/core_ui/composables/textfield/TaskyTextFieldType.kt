package com.artemissoftware.core_ui.composables.textfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

enum class TaskyTextFieldType {

    TEXT,
    EMAIL,
    PASSWORD;

    @Composable
    fun getKeyboardType(): KeyboardType = when (this) {
        EMAIL -> KeyboardType.Email
        PASSWORD -> KeyboardType.Password
        else -> KeyboardType.Text
    }
}
