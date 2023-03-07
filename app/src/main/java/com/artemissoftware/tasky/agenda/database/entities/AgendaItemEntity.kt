package com.artemissoftware.tasky.agenda.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemissoftware.tasky.agenda.domain.models.AgendaItemType

@Entity
data class AgendaItemEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String? = null,
    val remindAt: Long,
    val time: Long,
    val type: AgendaItemType
)
