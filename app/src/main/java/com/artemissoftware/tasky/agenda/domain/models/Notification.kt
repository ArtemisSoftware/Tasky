package com.artemissoftware.tasky.agenda.domain.models

data class Notification(
    val minutesBefore: Int,
    val description: String,
)
