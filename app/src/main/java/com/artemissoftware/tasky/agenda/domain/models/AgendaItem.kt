package com.artemissoftware.tasky.agenda.domain.models

import java.time.LocalDateTime

sealed class AgendaItem(
    val itemId: String,
    val itemTitle: String,
    val itemDescription: String? = null,
    val itemRemindAt: LocalDateTime,
    val itemTime: LocalDateTime
){

    data class Reminder(
        val id: String,
        val title: String,
        val description: String? = null,
        val remindAt: LocalDateTime,
        val time: LocalDateTime
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemTime = time
    )




}
