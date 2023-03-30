package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.BuildConfig.MAX_CHARACTERS_FOR_NAME
import com.artemissoftware.tasky.BuildConfig.MIN_CHARACTERS_FOR_NAME
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {

    operator fun invoke(name: String) = name.length in (MIN_CHARACTERS_FOR_NAME..MAX_CHARACTERS_FOR_NAME)
}
