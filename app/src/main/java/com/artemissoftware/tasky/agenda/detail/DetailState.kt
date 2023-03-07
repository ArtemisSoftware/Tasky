package com.artemissoftware.tasky.agenda.detail

import com.artemissoftware.tasky.agenda.AgendaItemType

data class DetailState(
    val isLoading: Boolean = false,
    val agendaItemType: AgendaItemType? = null
)
