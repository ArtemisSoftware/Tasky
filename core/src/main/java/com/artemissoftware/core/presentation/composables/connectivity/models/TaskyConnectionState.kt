package com.artemissoftware.core.presentation.composables.connectivity.models

sealed class TaskyConnectionState {
    object Available : TaskyConnectionState()
    object Unavailable : TaskyConnectionState()
}
