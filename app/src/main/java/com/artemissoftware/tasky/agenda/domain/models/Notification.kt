package com.artemissoftware.tasky.agenda.domain.models

data class Notification(
    val minutesBefore: Long,
    val description: String,
)
