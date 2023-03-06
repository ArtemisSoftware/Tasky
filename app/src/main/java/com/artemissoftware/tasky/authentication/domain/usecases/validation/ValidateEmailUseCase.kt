package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.authentication.domain.validators.EmailPatternValidator

class ValidateEmailUseCase(private val emailPatternValidator: EmailPatternValidator) {

    operator fun invoke(email: String): Boolean  = emailPatternValidator.isValidEmailPattern(email)
}