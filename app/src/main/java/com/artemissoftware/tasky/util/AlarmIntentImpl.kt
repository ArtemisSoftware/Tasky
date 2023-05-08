package com.artemissoftware.tasky.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.artemissoftware.core.util.AlarmIntent
import com.artemissoftware.tasky.MainActivity

class AlarmIntentImpl(
    private val context: Context,
): AlarmIntent {
    override fun getIntent(deeplink: Uri) = Intent(Intent.ACTION_VIEW, deeplink, context, MainActivity::class.java)
}