package com.artemissoftware.core_ui.composables.scaffold

import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.core_ui.composables.dialog.TYDialogType

class TYScaffoldState {

    var dialog = mutableStateOf<TYDialogType?>(null)
        private set

    fun showDialog(dialogType: TYDialogType) {
        dialog.value = dialogType
    }

    fun closeDialog() {
        dialog.value = null
    }

}