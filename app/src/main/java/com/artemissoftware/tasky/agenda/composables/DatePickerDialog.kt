package com.artemissoftware.tasky.agenda.composables

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.MutableState
import java.time.LocalDate

object DateTimePicker {

    fun datePickerDialog(
        context: Context,
        date: MutableState<LocalDate>,
        onDateSelected: (LocalDate) -> Unit = {},
    ): DatePickerDialog {
        return DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->

                date.value = LocalDate.of(mYear, mMonth + 1, mDayOfMonth)
                onDateSelected(date.value)
            },
            date.value.year,
            date.value.monthValue - 1,
            date.value.dayOfMonth,
        )
    }
}
