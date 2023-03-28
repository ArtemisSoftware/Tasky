package com.artemissoftware.tasky.agenda.presentation.edit.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class EditItem(val id: String, val editType: EditType) : Parcelable {

    data class Reminder(val reminderId: String, val reminderEditType: EditType) : EditItem(id = reminderId, editType = reminderEditType)

    data class Task(val taskId: String, val taskEditType: EditType) : EditItem(id = taskId, editType = taskEditType)

    data class Event(val eventId: String, val eventEditType: EditType) : EditItem(id = eventId, editType = eventEditType)
}
