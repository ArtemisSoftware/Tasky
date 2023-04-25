package com.artemissoftware.tasky.agenda.presentation.detail.mappers

import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.Visitor

fun Attendee.toVisitor(isEventCreator: Boolean): Visitor {
    return Visitor(
        attendee = this,
        isEventCreator = isEventCreator,
    )
}
