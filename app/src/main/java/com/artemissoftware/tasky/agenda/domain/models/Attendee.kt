package com.artemissoftware.tasky.agenda.domain.models

import java.time.LocalDateTime

data class Attendee(
    val fullName: String,
    val id: String,
    val email: String,
    val isGoing: Boolean,
    val remindAt: LocalDateTime,
)
