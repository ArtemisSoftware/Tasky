package com.artemissoftware.tasky.agenda.presentation.edit

import android.os.Parcelable
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditState(
    val editType: EditType = EditType.Title,
    val text: String = "",
) : Parcelable
