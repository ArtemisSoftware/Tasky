package com.artemissoftware.core.data.database.dao

import androidx.room.*
import com.artemissoftware.core.data.database.entities.AgendaItemEntity
import com.artemissoftware.core.domain.models.agenda.AgendaItemType

@Dao
interface AgendaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agendaItemEntity: AgendaItemEntity)

    @Query("SELECT * FROM agendaItemEntity WHERE id = :id")
    fun getAgendaItem(id: String): AgendaItemEntity?

    @Update
    fun update(agendaItemEntity: AgendaItemEntity)

    @Query("DELETE FROM agendaItemEntity WHERE id = :id")
    suspend fun delete(id: String)
}