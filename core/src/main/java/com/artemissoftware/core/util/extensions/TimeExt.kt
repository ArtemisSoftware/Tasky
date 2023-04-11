package com.artemissoftware.core.util.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}

fun LocalDateTime.toLong(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun LocalDateTime.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

fun LocalDate.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

fun LocalTime.format(pattern: String): String {
    return DateTimeFormatter.ofPattern(pattern).format(this)
}

fun LocalDate.toEpochMilli(): Long {
    val dateTime = LocalDateTime.of(this, LocalTime.now())
    return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}