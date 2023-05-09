package com.artemissoftware.core.data.alarm

import com.artemissoftware.core.util.UiText
import java.time.LocalDateTime

data class AlarmSpec(
    val id: String,
    val date: LocalDateTime,
    val title: UiText,
    val body: String,
    val deeplink: String
)
