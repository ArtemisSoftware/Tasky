package com.artemissoftware.tasky.agenda.presentation.edit.models

import android.os.Parcelable
import androidx.annotation.StringRes
import com.artemissoftware.tasky.R
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class EditType(
    @StringRes val title: Int,
) : Parcelable {
    object Title : EditType(title = R.string.edit_title)

    object Description : EditType(title = R.string.edit_description)
}
