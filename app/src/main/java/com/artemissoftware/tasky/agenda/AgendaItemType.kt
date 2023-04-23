package com.artemissoftware.tasky.agenda

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.artemissoftware.core.presentation.theme.*
import com.artemissoftware.tasky.R

sealed class AgendaItemType(
    @StringRes val title: Int,
    val color: Color,
    val borderColor: Color,
    val generalTextColor: Color = Black,
    val secondaryTextColor: Color = DarkGray,
    val bulletColor: Color = Black,
) {
    class Reminder() : AgendaItemType(color = Light2, borderColor = Gray, title = R.string.reminder)
    class Task(color: Color = Green) : AgendaItemType(color = color, borderColor = color, title = R.string.task, generalTextColor = White, secondaryTextColor = White)
    class Event(color: Color = LightGreen) : AgendaItemType(color = color, borderColor = color, title = R.string.event)
}
