package com.artemissoftware.core.domain.usecase

import com.artemissoftware.core.domain.alarm.AlarmScheduler
import com.artemissoftware.core.domain.repositories.UserRepository
import javax.inject.Inject

class DeleteAllUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val alarmScheduler: AlarmScheduler,
) {
    suspend operator fun invoke() {
        val ids = userRepository.deleteAllUserData()
        ids.forEach { id -> alarmScheduler.cancel(id) }
    }
}
