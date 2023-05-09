package com.artemissoftware.core.domain.usecase

import com.artemissoftware.core.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke() = userRepository.getUser()
}
