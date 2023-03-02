package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.BuildConfig.*

class ValidateUserNameUseCase {

    operator fun invoke(name: String): Boolean  {

        if (name.isEmpty()) {
            return false
        }
        return USER_NAME_REGEX.toRegex().matches(name)
    }

    companion object{

        private const val USER_NAME_REGEX = "^[A-Za-z0-9\\s\\S]{$MIN_CHARACTERS_FOR_NAME,$MAX_CHARACTERS_FOR_NAME}\$"
    }
}