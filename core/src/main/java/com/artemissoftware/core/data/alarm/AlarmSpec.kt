package com.artemissoftware.core.data.alarm

import java.time.LocalDateTime

data class AlarmSpec(
    val id: String,
    val date: LocalDateTime,
    val title: String,
    val body: String,
)
