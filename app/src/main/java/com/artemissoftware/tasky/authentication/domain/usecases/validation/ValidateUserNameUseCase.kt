package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.BuildConfig.*

class ValidateUserNameUseCase {

    operator fun invoke(name: String) = name.length in (MIN_CHARACTERS_FOR_NAME..MAX_CHARACTERS_FOR_NAME)

}