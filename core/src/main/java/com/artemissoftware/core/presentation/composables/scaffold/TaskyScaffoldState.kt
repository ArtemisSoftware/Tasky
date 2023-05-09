package com.artemissoftware.core.presentation.composables.scaffold

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class TaskyScaffoldState : Parcelable {

    @IgnoredOnParcel
    var dialog = mutableStateOf<TaskyDialogType?>(null)
        private set

    fun showDialog(dialogType: TaskyDialogType) {
        dialog.value = dialogType
    }

    fun closeDialog() {
        dialog.value = null
    }

    @IgnoredOnParcel
    val taskySnackBarState = TaskySnackBarState()
}
