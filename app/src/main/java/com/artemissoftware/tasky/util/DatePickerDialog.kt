package com.artemissoftware.tasky.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.time.LocalDate

object DateTimePicker {

    fun datePickerDialog(
        context: Context,
        date: LocalDate,
        onDateSelected: (LocalDate) -> Unit = {},
    ): DatePickerDialog {
        return DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                onDateSelected(LocalDate.of(mYear, mMonth + 1, mDayOfMonth))
            },
            date.year,
            date.monthValue - 1,
            date.dayOfMonth,
        )
    }
}
