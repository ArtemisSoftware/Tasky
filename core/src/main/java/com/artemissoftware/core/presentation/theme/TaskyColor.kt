package com.artemissoftware.core.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF16161C)
val DarkGray = Color(0xFF5C5D5A)
val Gray = Color(0xFFA9B4BE)
val DarkGreen = Color(0xFF06572A)
val Green = Color(0xFF279F70)
val LightBlue = Color(0xFFB7C6DE)
val Light = Color(0xFFEEF6FF)
val Light2 = Color(0xFFF2F3F7)
val White = Color(0xFFFFFFFF)
val Brown = Color(0xFF40492D)
val Orange = Color(0xFFFDEFA8)

val HintTextColor = Color(0xFFA1A4B2)
val UnfocusedTrailingIconColor = Color(0xFFBEBEBE)
val ErrorRed = Color(0xFFFF7272)

val Silver = Color(0xFFA9B4BE)
val Link = Color(0xFF8E97FD)

val LightGreen = Color(0xFFCAEF45)

val Colors.LoadingBackground: Color
    get() = if (isLight) Color(0xffffffff) else Color(0xff252525)