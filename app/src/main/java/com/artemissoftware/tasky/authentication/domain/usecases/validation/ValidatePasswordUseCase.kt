package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.BuildConfig.MIN_CHARACTERS_FOR_PASSWORD
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        val hasValidLength = password.length >= MIN_CHARACTERS_FOR_PASSWORD
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigits = password.any { it.isDigit() }

        return hasValidLength && hasUppercase && hasLowercase && hasDigits
    }
}
