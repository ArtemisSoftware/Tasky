package com.artemissoftware.tasky

import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.tasky.navigation.Destination

data class MainState(
    val destinationAfterSplash: Destination? = null,
    val showSplash: Boolean = true,
    val scaffoldState: TaskyScaffoldState = TaskyScaffoldState()
)
