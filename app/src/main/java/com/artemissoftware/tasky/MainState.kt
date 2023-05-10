package com.artemissoftware.tasky

import com.artemissoftware.core.presentation.composables.scaffold.TaskyDialogState
import com.artemissoftware.tasky.destinations.DirectionDestination

data class MainState(
    val destinationAfterSplash: DirectionDestination? = null,
    val showSplash: Boolean = true,
    val taskyDialogState: TaskyDialogState = TaskyDialogState(),
)
