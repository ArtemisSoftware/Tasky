package com.artemissoftware.core.util.extensions

fun String.asInitials(limit: Int = 2): String {
    val buffer = StringBuffer()
    trim().split(" ").filter {
        it.isNotEmpty()
    }.joinTo(
        buffer = buffer,
        limit = limit,
        separator = "",
        truncated = "",
    ) { s ->
        s.first().uppercase()
    }
    return buffer.toString()
}