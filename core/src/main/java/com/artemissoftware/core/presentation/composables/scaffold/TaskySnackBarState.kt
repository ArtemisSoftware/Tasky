package com.artemissoftware.core.presentation.composables.scaffold

import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.core.presentation.composables.snackbar.TaskySnackBarType

class TaskySnackBarState {

    var snackbar = mutableStateOf<TaskySnackBarType?>(null)
        private set

    fun show(dialogType: TaskySnackBarType) {
        snackbar.value = dialogType
    }

    fun close() {
        snackbar.value = null
    }
}
