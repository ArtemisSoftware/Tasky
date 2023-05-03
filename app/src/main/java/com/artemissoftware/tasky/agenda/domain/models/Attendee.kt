package com.artemissoftware.tasky.agenda.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Attendee(
    val fullName: String,
    val id: String,
    val email: String,
    val isGoing: Boolean,
    val remindAt: LocalDateTime,
) : Parcelable
