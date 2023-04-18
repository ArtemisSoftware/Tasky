package com.artemissoftware.tasky.agenda.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Picture(val id: String, val source: String) : Parcelable {

    data class Local(val picId: String, val uri: String) : Picture(source = uri, id = picId)

    data class Remote(val key: String, val url: String) : Picture(source = url, id = key)
}
