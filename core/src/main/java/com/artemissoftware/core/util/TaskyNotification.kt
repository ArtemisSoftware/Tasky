package com.artemissoftware.core.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.artemissoftware.core.R

object TaskyNotification {

    fun sendNotification(context: Context, id: String, title: String, body: String, pendingIntent: PendingIntent) {
        createChannel(context = context)
        createAndSendNotification(context, id = id, title = title, body = body, pendingIntent = pendingIntent)
    }

    private fun createChannel(context: Context) {
        val channel = NotificationChannel(
            context.getString(R.string.tasky_channel_id),
            context.getString(R.string.tasky_channel_name),
            NotificationManager.IMPORTANCE_HIGH,
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createAndSendNotification(context: Context, id: String, title: String, body: String, pendingIntent: PendingIntent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, context.getString(R.string.tasky_channel_id))
            .setSmallIcon(R.drawable.ic_tasky_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id.hashCode(), notification)
    }
}
