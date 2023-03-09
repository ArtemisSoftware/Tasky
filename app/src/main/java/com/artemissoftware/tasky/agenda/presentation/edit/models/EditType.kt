package com.artemissoftware.tasky.agenda.presentation.edit.models

import androidx.annotation.StringRes
import com.artemissoftware.tasky.R

sealed class EditType(
    @StringRes val title: Int
){
    object Title: EditType(title = R.string.edit_title)

    object Description: EditType(title = R.string.edit_description)

}
