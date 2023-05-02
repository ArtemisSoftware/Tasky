package com.artemissoftware.tasky.agenda.domain.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.artemissoftware.core.presentation.theme.Black
import com.artemissoftware.core.presentation.theme.DarkGray
import com.artemissoftware.core.presentation.theme.Gray
import com.artemissoftware.core.presentation.theme.Green
import com.artemissoftware.core.presentation.theme.Light2
import com.artemissoftware.core.presentation.theme.LightGreen
import com.artemissoftware.core.presentation.theme.White
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.agenda.util.NavigationConstants.EVENT_DETAIL_ROUTE
import com.artemissoftware.tasky.agenda.util.NavigationConstants.REMINDER_DETAIL_ROUTE
import com.artemissoftware.tasky.agenda.util.NavigationConstants.TASK_DETAIL_ROUTE

sealed class AgendaItemType(
    @StringRes val title: Int,
    val color: Color,
    val borderColor: Color,
    val generalTextColor: Color = Black,
    val secondaryTextColor: Color = DarkGray,
    val bulletColor: Color = Black,
    val detailDeepLink: String,
) {
    object Reminder : AgendaItemType(color = Light2, borderColor = Gray, title = R.string.reminder, detailDeepLink = REMINDER_DETAIL_ROUTE)
    object Task : AgendaItemType(color = Green, borderColor = Green, title = R.string.task, generalTextColor = White, secondaryTextColor = White, detailDeepLink = TASK_DETAIL_ROUTE)
    object Event : AgendaItemType(color = LightGreen, borderColor = LightGreen, title = R.string.event, detailDeepLink = EVENT_DETAIL_ROUTE)

    companion object {
        fun convertAgendaItem(agendaItem: AgendaItem): AgendaItemType {
            return when (agendaItem) {
                is AgendaItem.Reminder -> Reminder
                is AgendaItem.Task -> Task
                is AgendaItem.Event -> Event
            }
        }
    }
}
