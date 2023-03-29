package com.artemissoftware.core.presentation.events

import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType

sealed class UiEvent {

    data class ShowDialog(val dialogType: TaskyDialogType): UiEvent()

    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent()

    data class PopBackStackWithArguments<T>(val arguments: T): UiEvent()

}
