package com.artemissoftware.tasky.agenda.domain.models

import java.time.LocalDateTime

sealed class AgendaItem(
    val itemId: String? = null,
    val itemTitle: String,
    val itemDescription: String? = null,
    val itemRemindAt: Long,
    val itemTime: LocalDateTime
){

    data class Reminder(
        val id: String? = null,
        val title: String,
        val description: String? = null,
        val remindAt: Long,
        val time: LocalDateTime
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemTime = time
    )




}
