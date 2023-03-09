package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.models.agenda.AgendaItemType

sealed class AgendaItem(
    val itemId: String? = null,
    val itemTitle: String,
    val itemDescription: String? = null,
    val itemRemindAt: Long,
    val itemTime: Long
){

    data class Reminder(
        val id: String? = null,
        val title: String,
        val description: String? = null,
        val remindAt: Long,
        val time: Long
    ) : AgendaItem(
        itemId = id,
        itemTitle = title,
        itemDescription = description,
        itemRemindAt = remindAt,
        itemTime = time
    )




}
