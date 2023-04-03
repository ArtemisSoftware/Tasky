package com.artemissoftware.tasky.agenda.domain.models

data class Notification(
    val id: Int,
    val minutesBefore: Long,
    val description: String,
    val isDefault: Boolean,
)
