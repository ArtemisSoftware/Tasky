package com.artemissoftware.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemissoftware.core.domain.SyncType

@Entity
data class AttendeeSyncEntity(
    @PrimaryKey
    val id: String,
    val syncType: SyncType,
)
