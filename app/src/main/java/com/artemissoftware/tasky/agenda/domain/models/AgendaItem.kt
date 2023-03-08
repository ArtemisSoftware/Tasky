package com.artemissoftware.tasky.agenda.domain.models

import com.artemissoftware.core.domain.models.agenda.AgendaItemType

data class AgendaItem(
    val id: String? = null,
    val title: String,
    val description: String? = null,
    val remindAt: Long,
    val time: Long,
    val type: AgendaItemType
)
