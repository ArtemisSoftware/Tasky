package com.artemissoftware.core.domain.usecase

import com.artemissoftware.core.domain.repositories.UserRepository
import javax.inject.Inject

class DeleteAllUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        userRepository.deleteAllUserData()
    }
}
