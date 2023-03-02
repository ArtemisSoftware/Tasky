package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.BuildConfig.*

class ValidatePasswordUseCase {

    operator fun invoke(password: String): Boolean  {

        if (password.isEmpty() || password.isBlank()) {
            return false
        }

        return PASSWORD_REGEX.toRegex().matches(password)
    }

    companion object{

        private const val PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{$MIN_CHARACTERS_FOR_PASSWORD,}\$"
    }
}