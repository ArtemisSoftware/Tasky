package com.artemissoftware.tasky.agenda.presentation.detail.mappers

import com.artemissoftware.tasky.agenda.domain.models.Attendee
import com.artemissoftware.tasky.agenda.presentation.detail.eventdetail.models.Visitor

fun Attendee.toVisitor(isEventCreator: Boolean): Visitor {
    return Visitor(
        attendee = this,
        isEventCreator = isEventCreator,
    )
}

fun List<Attendee>.toNotGoingVisitors(): List<Visitor> {
    return this.filter { !it.isGoing }.map { it.toVisitor(isEventCreator = false) }
}

fun List<Attendee>.toGoingVisitors(hostId: String): List<Visitor> {
    val list = this.filter { it.isGoing }.map { it.toVisitor(isEventCreator = (it.id == hostId)) }.toMutableList()
    list.sortBy { !it.isEventCreator }
    return list.toList()
}
