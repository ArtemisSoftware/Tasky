package com.artemissoftware.tasky

import com.artemissoftware.tasky.destinations.DirectionDestination

data class MainState(
    val destinationAfterSplash: DirectionDestination? = null,
    val showSplash: Boolean = true,
)
