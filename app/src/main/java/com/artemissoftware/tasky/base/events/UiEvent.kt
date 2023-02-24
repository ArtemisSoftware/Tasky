package com.artemissoftware.tasky.base.events

sealed class UiEvent {

    data class ShowDialog(val dialogType: String): UiEvent() // TODO : change the argument to val dialogType: TYDialogType

    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent()

}
