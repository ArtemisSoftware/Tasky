package com.artemissoftware.core.data.database.converters

import androidx.room.TypeConverter
import com.artemissoftware.core.domain.models.agenda.AgendaItemType

class AgendaItemTypeConverter {

    @TypeConverter
    fun fromAgendaItemType(type: AgendaItemType): String {
        return type.name
    }

    @TypeConverter
    fun toAgendaItemType(type: String): AgendaItemType {
        return AgendaItemType.valueOf(type)
    }

}