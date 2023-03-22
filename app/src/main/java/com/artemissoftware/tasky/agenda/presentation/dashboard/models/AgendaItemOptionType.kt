package com.artemissoftware.tasky.agenda.presentation.dashboard.models

import androidx.annotation.StringRes
import com.artemissoftware.tasky.R

enum class AgendaItemOptionType(@StringRes val descriptionId: Int) {

    EVENT(descriptionId = R.string.event),
    TASK(descriptionId = R.string.task),
    REMINDER(descriptionId = R.string.reminder),
}
