package com.artemissoftware.core.presentation.events

import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.composables.snackbar.TaskySnackBarType

sealed class UiEvent {

    data class ShowDialog(val dialogType: TaskyDialogType) : UiEvent()

    object PopBackStack : UiEvent()

    data class Navigate(val route: String) : UiEvent()

    data class NavigateAndPopCurrent(val route: String) : UiEvent()

    data class PopBackStackWithArguments<T>(val arguments: T) : UiEvent()

    data class ShowSnackBar(val snackBarType: TaskySnackBarType) : UiEvent()
}
