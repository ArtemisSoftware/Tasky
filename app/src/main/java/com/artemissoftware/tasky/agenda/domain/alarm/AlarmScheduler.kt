package com.artemissoftware.tasky.agenda.domain.alarm

import com.artemissoftware.core.data.alarm.AlarmSpec

interface AlarmScheduler {

    fun schedule(alarmSpec: AlarmSpec)
    fun cancel(id: String)
}
