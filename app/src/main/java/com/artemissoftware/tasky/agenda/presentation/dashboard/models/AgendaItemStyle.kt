package com.artemissoftware.tasky.agenda.presentation.dashboard.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.artemissoftware.tasky.R
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light2
import com.artemissoftware.core.presentation.theme.LightGreen
import com.artemissoftware.core.presentation.theme.White

sealed class AgendaItemStyle(
    @StringRes val title: Int,
    val color: Color,
    val borderColor: Color,
    val generalTextColor: Color = Black,
    val secondaryTextColor: Color = DarkGray,
    val bulletColor: Color = Black,
) {
    object Reminder : AgendaItemStyle(color = Light2, borderColor = Gray, title = R.string.reminder)
    object Task : AgendaItemStyle(color = Green, borderColor = Green, title = R.string.task, generalTextColor = White, secondaryTextColor = White)
    object Event : AgendaItemStyle(color = LightGreen, borderColor = LightGreen, title = R.string.event)
}
