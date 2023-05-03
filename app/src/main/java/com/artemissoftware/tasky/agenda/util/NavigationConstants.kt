package com.artemissoftware.tasky.agenda.util

object NavigationConstants {
    const val ID = "id"
    const val IS_EDITING = "isEditing"

    const val TASKY_HOST = "https://tasky.com"

    const val REMINDER_DETAIL_ROUTE = "$TASKY_HOST/reminder_detail_screen?id={id}&isEditing={isEditing}"
    const val TASK_DETAIL_ROUTE = "$TASKY_HOST/task_detail_screen?id={id}&isEditing={isEditing}"
    const val EVENT_DETAIL_ROUTE = "$TASKY_HOST/event_detail_screen?id={id}&isEditing={isEditing}"
}
