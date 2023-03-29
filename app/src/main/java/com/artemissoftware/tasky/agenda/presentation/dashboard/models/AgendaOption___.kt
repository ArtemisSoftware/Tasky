package com.artemissoftware.tasky.agenda.presentation.dashboard.models

import androidx.annotation.StringRes
import com.artemissoftware.tasky.R

enum class AgendaItemOption(@StringRes val descriptionId: Int) {

    OPEN(descriptionId = R.string.open),
    EDIT(descriptionId = R.string.edit),
    DELETE(descriptionId = R.string.delete),
}

enum class AgendaUserOption(@StringRes val descriptionId: Int) {

    LOG_OUT(descriptionId = R.string.log_out),
}

enum class AgendaItems(@StringRes val descriptionId: Int) {

    EVENT(descriptionId = R.string.event),
    TASK(descriptionId = R.string.task),
    REMINDER(descriptionId = R.string.reminder),
}
