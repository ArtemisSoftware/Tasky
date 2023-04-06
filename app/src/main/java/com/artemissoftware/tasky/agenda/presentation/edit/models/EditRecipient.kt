package com.artemissoftware.tasky.agenda.presentation.edit.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditRecipient(val text: String, val editType: EditType) : Parcelable
