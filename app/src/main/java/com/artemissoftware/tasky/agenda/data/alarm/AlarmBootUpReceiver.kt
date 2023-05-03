package com.artemissoftware.tasky.agenda.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.artemissoftware.tasky.agenda.data.mappers.toAlarmSpec
import com.artemissoftware.tasky.agenda.domain.alarm.AlarmScheduler
import com.artemissoftware.tasky.agenda.domain.usecase.agenda.GetFutureAgendaItemsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBootUpReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getFutureAgendaItemsUseCase: GetFutureAgendaItemsUseCase

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    private val job = SupervisorJob()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(job).launch {
                val items = getFutureAgendaItemsUseCase()
                items.map { it.toAlarmSpec() }.forEach { alarmSpec ->
                    alarmScheduler.schedule(alarmSpec)
                }
            }
        }
    }
}
