package com.artemissoftware.core.util.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}

fun LocalDateTime.toLong(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun LocalDateTime.format(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, HH:mm")
    return this.format(formatter)
}

fun LocalDate.format(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM YYYY")
    return this.format(formatter)
}