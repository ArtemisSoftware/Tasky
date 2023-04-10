package com.artemissoftware.tasky.agenda.presentation.detail.composables.dialog

import androidx.compose.runtime.mutableStateOf

class AttendeeDialogState {

    var show = mutableStateOf(false)
        private set

    fun showDialog() {
        show.value = true
    }

    fun closeDialog() {
        show.value = false
    }
}
