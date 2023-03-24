package com.artemissoftware.core.domain.usecase

import com.artemissoftware.core.domain.repositories.UserStoreRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
) {
    suspend operator fun invoke() = userStoreRepository.getUser()
}
