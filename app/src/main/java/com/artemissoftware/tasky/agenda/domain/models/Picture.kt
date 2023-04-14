package com.artemissoftware.tasky.agenda.domain.models

sealed class Picture {

    data class Local(val uri: String) : Picture()

    data class Remote(val key: String, val url: String) : Picture()
}
