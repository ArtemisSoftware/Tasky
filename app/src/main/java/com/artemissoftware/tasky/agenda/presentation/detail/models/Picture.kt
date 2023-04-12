package com.artemissoftware.tasky.agenda.presentation.detail.models

import android.net.Uri

sealed class Picture {

    data class Local(val uri: Uri) : Picture()

    data class Remote(val key: String, val url: String) : Picture()
}
