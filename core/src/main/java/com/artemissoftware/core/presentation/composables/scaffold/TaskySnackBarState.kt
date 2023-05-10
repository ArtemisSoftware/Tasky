package com.artemissoftware.core.presentation.composables.scaffold

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.core.presentation.composables.snackbar.TaskySnackBarType
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class TaskySnackBarState : Parcelable {

    @IgnoredOnParcel
    var snackbar = mutableStateOf<TaskySnackBarType?>(null)
        private set

    fun show(dialogType: TaskySnackBarType) {
        snackbar.value = dialogType
    }

    fun close() {
        snackbar.value = null
    }
}
