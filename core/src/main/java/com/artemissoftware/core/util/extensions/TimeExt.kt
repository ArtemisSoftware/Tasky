package com.artemissoftware.core.util.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
}

fun LocalDateTime.toLong(): Long {
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

