package com.artemissoftware.tasky.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.core.presentation.composables.navigation.ManageUIEvents
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.core.presentation.events.TaskyEvents
import com.artemissoftware.core.presentation.events.TaskyUiEventViewModel

interface NavigationRoute<E: TaskyEvents, T : TaskyUiEventViewModel<E>> {

    fun getDestination(): Destination

    /**
     * Returns the screen's ViewModel. Needs to be overridden so that Hilt can generate code for the factory for the ViewModel class.
     */
    @Composable
    fun viewModel(): T


    /**
     * Returns the screen's content.
     */
    @Composable
    fun Content(viewModel: T)

    /**
     * Generates the composable for this route.
     */
    fun composable(
        navGraphBuilder: NavGraphBuilder,
        scaffoldState: TaskyScaffoldState,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = getDestination().getFullRoute(),
            arguments = getDestination().arguments,
        ) {

            val viewModel = viewModel()

            Content(viewModel)

            ManageUIEvents(
                uiEvent = viewModel.uiEvent,
                scaffoldState = scaffoldState,
                onPopBackStack = {
                    navController.popBackStack()
                },
                onNavigate =  { event->
                    navController.navigate(event.route)
                },
            )
        }
    }


}
