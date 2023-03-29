package com.artemissoftware.tasky.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.runtime.MutableState
import java.time.LocalDate
import java.time.LocalTime

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

    fun timePickerDialog(
        context: Context,
        time: LocalTime,
        onTimeSelected: (LocalTime) -> Unit = {},
    ): TimePickerDialog {

        return TimePickerDialog(
            context,
            {_, mHour : Int, mMinute: Int ->
                onTimeSelected(LocalTime.of(mHour, mMinute))
            },
            time.hour,
            time.minute,
            true
        )
    }
}
