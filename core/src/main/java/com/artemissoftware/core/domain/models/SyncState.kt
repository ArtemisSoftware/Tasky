package com.artemissoftware.core.domain.models

import com.artemissoftware.core.domain.SyncType

data class SyncState(val itemId: String, val syncType: SyncType)
