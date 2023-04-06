package com.artemissoftware.tasky.agenda.data.alarm

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.artemissoftware.core.util.TaskyNotification
import com.artemissoftware.core.util.safeLet
import com.artemissoftware.tasky.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        safeLet(context, intent?.extras) { currentContext, extras ->

            extras.getString(ID)?.let { id ->
                val title = extras.getString(TITLE).orEmpty()
                val body = extras.getString(BODY).orEmpty()

                TaskyNotification.sendNotification(
                    context = currentContext,
                    id = id,
                    title = title,
                    body = body,
                    pendingIntent = getPendingIntent(context = currentContext),
                )
            }
        }
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        // TODO: how to get the destinations here if we are on the data module that does not know about navigation?
        val deeplink = "https://tasky.com".toUri()

        val intent = Intent(Intent.ACTION_VIEW, deeplink, context, MainActivity::class.java)

        val pendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_IMMUTABLE)
        }

        return pendingIntent
    }

    companion object {
        const val ID = "ID"
        const val TITLE = "TITLE"
        const val BODY = "BODY"
        private const val REQUEST_CODE = 1
    }
}
