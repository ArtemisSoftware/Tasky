package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models

import androidx.annotation.StringRes
import com.artemissoftware.tasky.agenda.domain.models.Attendee

sealed class VisitorEvent {

    data class Title(@StringRes val textId: Int) : VisitorEvent()

    data class Visitor(val attendee: Attendee, val isEventCreator: Boolean) : VisitorEvent()
}
