package com.artemissoftware.tasky.navigation

import com.artemissoftware.core.presentation.navigation.BaseDestination
import com.artemissoftware.core.presentation.navigation.models.CustomNavigationArgument

sealed class Destination(
    route: String,
    customArguments: List<CustomNavigationArgument> = emptyList(),
) : BaseDestination(route = route, customArguments = customArguments) {

    object Login : Destination(route = "LOGIN")
    object Register : Destination(route = "REGISTER")
}
