package com.artemissoftware.tasky.agenda.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Picture(val source: String) : Parcelable {

    data class Local(val uri: String) : Picture(source = uri)

    data class Remote(val key: String, val url: String) : Picture(source = url)
}
