package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemissoftware.core.domain.models.agenda.AgendaItemType

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
