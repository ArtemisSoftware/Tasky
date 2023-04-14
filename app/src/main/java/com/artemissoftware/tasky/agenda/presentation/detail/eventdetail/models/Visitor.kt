package com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models

import com.artemissoftware.tasky.agenda.domain.models.Attendee

data class Visitor(val attendee: Attendee, val isEventCreator: Boolean)
