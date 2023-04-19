package com.artemissoftware.core.domain

enum class SyncType {

    SYNCED,
    CREATE,
    UPDATE,
    DELETE;

    companion object {
        fun getSyncTypeByName(name: String): SyncType? {
            return values().firstOrNull { it.name.equals(name, true) }
        }
    }
}
