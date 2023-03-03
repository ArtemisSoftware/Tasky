package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.BuildConfig.*

class ValidateUserNameUseCase {

    operator fun invoke(name: String): Boolean  {

        val hasValidLength =  name.length in (MIN_CHARACTERS_FOR_NAME..MAX_CHARACTERS_FOR_NAME)
        return hasValidLength && name.isNotBlank()
    }

}