package com.artemissoftware.tasky.agenda

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.artemissoftware.tasky.R

sealed class Assignment( // TODO: mudar cores com as da core
    @StringRes val title: Int,
    val color: Color,
    val borderColor: Color
){
    class Reminder(): Assignment(color = Color.Red, borderColor = Color.Green, title = R.string.reminder)
    class Task(color: Color = Color.Blue): Assignment(color = color, borderColor = color, title = R.string.task)
    class Event(color: Color = Color.Magenta): Assignment(color = Color.Red, borderColor = Color.Green, title = R.string.event)
}
