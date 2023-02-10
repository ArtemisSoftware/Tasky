package com.artemissoftware.core_ui.textfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

enum class TYTextFieldType {

    TEXT,
    EMAIL,
    PASSWORD;

    @Composable
    fun getKeyboardType(): KeyboardType = when (this) {
        EMAIL -> KeyboardType.Email
        PASSWORD -> KeyboardType.Password
        else -> KeyboardType.Text
    }

    fun getMaxChar(): Int = when (this) {
        EMAIL -> 28
        else -> 32
    }
}
