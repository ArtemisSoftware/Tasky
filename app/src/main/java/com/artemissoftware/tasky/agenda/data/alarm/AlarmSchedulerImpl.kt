package com.artemissoftware.tasky.agenda.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.artemissoftware.core.data.alarm.AlarmReceiver
import com.artemissoftware.core.data.alarm.AlarmSpec
import com.artemissoftware.core.data.alarm.AlarmReceiver.Companion.BODY
import com.artemissoftware.core.data.alarm.AlarmReceiver.Companion.ID
import com.artemissoftware.core.data.alarm.AlarmReceiver.Companion.TITLE
import com.artemissoftware.core.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItemType
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmSpec: AlarmSpec) {
        cancel(id = alarmSpec.id)
        setAlarm(alarmSpec = alarmSpec)
    }

    private fun setAlarm(alarmSpec: AlarmSpec) = with(alarmSpec) {
        if (date.isAfter(LocalDateTime.now())) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                date.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    context,
                    id.hashCode(),
                    getIntent(this),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                ),
            )
        }
    }

    override fun cancel(id: String) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            ),
        )
    }

    private fun getIntent(alarmSpec: AlarmSpec): Intent {
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putString(TITLE, alarmSpec.title)
            putString(BODY, alarmSpec.body)
            putString(ID, alarmSpec.id)
            putString(LINK, AgendaItemType.convertAgendaItem(item).detailDeepLink)
        }
        return intent.apply {
            putExtras(bundle)
        }
    }
}
