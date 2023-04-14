package com.artemissoftware.tasky.authentication.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManageUIEvents(
    uiEvent: Flow<UiEvent>,
    showDialog: (TaskyDialogType) -> Unit = {},
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    onNavigateAndPopCurrent: (UiEvent.NavigateAndPopCurrent) -> Unit = {},
    onPopBackStack: () -> Unit = {},
    onPopBackStackWithArguments: (UiEvent.PopBackStackWithArguments<*>) -> Unit = {},
    onShowSnackBar: (UiText) -> Unit = {},
) {
    LaunchedEffect(key1 = Unit) {
        uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowDialog -> {
                    showDialog.invoke(event.dialogType)
                }
                is UiEvent.PopBackStack -> { onPopBackStack.invoke() }
                is UiEvent.PopBackStackWithArguments<*> -> { onPopBackStackWithArguments(event) }
                is UiEvent.Navigate -> { onNavigate(event) }
                is UiEvent.NavigateAndPopCurrent -> {
                    onNavigateAndPopCurrent(event)
                }
                is UiEvent.ShowSnackBar -> {
                    onShowSnackBar.invoke(event.uiText)
                }
            }
        }
    }
}
