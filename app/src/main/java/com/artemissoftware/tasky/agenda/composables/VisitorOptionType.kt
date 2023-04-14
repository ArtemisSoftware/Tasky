package com.artemissoftware.tasky.agenda.composables

import androidx.annotation.StringRes
import com.artemissoftware.tasky.R

enum class VisitorOptionType(@StringRes val textId: Int) {
    ALL(textId = R.string.all),
    GOING(textId = R.string.going),
    NOT_GOING(textId = R.string.not_going),
}
