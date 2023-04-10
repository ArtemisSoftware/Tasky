package com.artemissoftware.tasky.agenda.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.models.AgendaItem
import com.artemissoftware.tasky.agenda.domain.models.AgendaItemType
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AgendaItem) {
        if (item.itemRemindAt.isAfter(LocalDateTime.now())) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                item.starDate.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    context,
                    item.hashCode(),
                    getIntent(item),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                ),
            )
        }
    }

    override fun cancel(item: AgendaItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            ),
        )
    }

    private fun getIntent(item: AgendaItem): Intent {
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putString(TITLE, AgendaItemType.convertAgendaItem(item).name)
            putString(BODY, item.itemTitle)
        }
        return intent.apply {
            putExtras(bundle)
        }
    }

    companion object {
        const val TITLE = "TITLE"
        const val BODY = "BODY"
    }
}
