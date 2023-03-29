package com.artemissoftware.core.presentation.composables.scaffold

import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType

class TaskyScaffoldState {

    var dialog = mutableStateOf<TaskyDialogType?>(null)
        private set

    fun showDialog(dialogType: TaskyDialogType) {
        dialog.value = dialogType
    }

    fun closeDialog() {
        dialog.value = null
    }

}