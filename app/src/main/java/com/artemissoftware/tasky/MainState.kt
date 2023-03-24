package com.artemissoftware.tasky

import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.tasky.navigation.Destination

data class MainState(
    val screenToNavigate: Destination? = null,
    val isLoading: Boolean = true,
    val scaffoldState: TaskyScaffoldState = TaskyScaffoldState()
)
