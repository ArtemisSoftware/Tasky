package com.artemissoftware.core.util

import android.content.Intent
import android.net.Uri

interface AlarmIntent {

    fun getIntent(deeplink: Uri) : Intent
}