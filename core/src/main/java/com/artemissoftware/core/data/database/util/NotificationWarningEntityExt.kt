package com.artemissoftware.core.data.database.util

import com.artemissoftware.core.data.database.entities.NotificationWarningEntity
import java.time.Duration
import java.time.LocalDateTime

fun List<NotificationWarningEntity>.getValidNotification(remindAt: LocalDateTime, time: LocalDateTime): NotificationWarningEntity? {
    return try {
        val notificationDefault = this.find { it.isDefault } ?: this.first()
        val notification = this.find { Duration.between(remindAt, time).toMinutes() == it.minutesBefore }
        notification ?: notificationDefault
    } catch (ex: NoSuchElementException) {
        null
    }
}
