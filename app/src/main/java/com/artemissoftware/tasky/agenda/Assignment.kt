package com.artemissoftware.tasky.agenda

import androidx.compose.ui.graphics.Color

sealed class Assignment(
    color: Color,
    borderColor: Color
){
    data class Reminder(val color: Color, val borderColor: Color): Assignment(color = color, borderColor = borderColor)
    data class Task(val color: Color): Assignment(color = color, borderColor = color)
    data class Event(val color: Color): Assignment(color = color, borderColor = color)
}
