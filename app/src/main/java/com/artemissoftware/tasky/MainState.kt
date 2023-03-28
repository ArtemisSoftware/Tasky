package com.artemissoftware.tasky

import com.artemissoftware.core.presentation.composables.scaffold.TaskyDialogState
import com.artemissoftware.tasky.navigation.Destination

data class MainState(
    val destinationAfterSplash: Destination? = null,
    val showSplash: Boolean = true,
    val taskyDialogState: TaskyDialogState = TaskyDialogState()
)
