package com.artemissoftware.tasky.agenda.presentation.detail.reminderdetail

import android.os.Parcelable
import com.artemissoftware.tasky.agenda.presentation.edit.models.EditType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReminderRecipient(val text: String, val editType: EditType) : Parcelable
