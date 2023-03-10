package com.artemissoftware.core.presentation.composables.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.artemissoftware.core.presentation.composables.scaffold.TaskyScaffoldState
import com.artemissoftware.core.presentation.events.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ManageUIEvents(
    uiEvent: Flow<UiEvent>,
    scaffoldState: TaskyScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    onPopBackStack: () -> Unit = {},
) {

    LaunchedEffect(key1 = Unit) {

        uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowDialog -> {
                    scaffoldState.showDialog(event.dialogType)
                }
                is UiEvent.PopBackStack -> { onPopBackStack.invoke() }
                is UiEvent.Navigate -> { onNavigate(event) }
            }
        }
    }
}