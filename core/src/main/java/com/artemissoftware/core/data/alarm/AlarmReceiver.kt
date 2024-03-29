package com.artemissoftware.core.data.alarm

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.artemissoftware.core.util.AlarmIntent
import com.artemissoftware.core.util.constants.TaskyConstants.TASKY_HOST
import com.artemissoftware.core.util.notifications.TaskyNotification
import com.artemissoftware.core.util.safeLet
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmIntent: AlarmIntent

    override fun onReceive(context: Context?, intent: Intent?) {
        safeLet(context, intent?.extras) { currentContext, extras ->

            extras.getString(ID)?.let { id ->
                val title = extras.getString(TITLE).orEmpty()
                val body = extras.getString(BODY).orEmpty()
                val deeplink = extras.getString(LINK, TASKY_HOST)

                TaskyNotification.sendNotification(
                    context = currentContext,
                    id = id,
                    title = title,
                    body = body,
                    pendingIntent = getPendingIntent(context = currentContext, deeplink = deeplink),
                )
            }
        }
    }

    private fun getPendingIntent(context: Context, deeplink: String): PendingIntent {
        val pendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(alarmIntent.getIntent(deeplink.toUri()))
            getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_IMMUTABLE)
        }

        return pendingIntent
    }

    companion object {
        const val ID = "ID"
        const val TITLE = "TITLE"
        const val BODY = "BODY"
        const val LINK = "LINK"
        private const val REQUEST_CODE = 1
    }
}
