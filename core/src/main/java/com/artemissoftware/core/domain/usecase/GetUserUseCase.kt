package com.artemissoftware.core.domain.usecase

import com.artemissoftware.core.domain.repositories.UserStoreRepository

class GetUserUseCase constructor(
    private val userStoreRepository: UserStoreRepository,
) {
    suspend operator fun invoke() = userStoreRepository.getUser()
}
