package com.artemissoftware.tasky.authentication.domain.usecases.validation

import com.artemissoftware.tasky.authentication.domain.validators.EmailPatternValidator
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(private val emailPatternValidator: EmailPatternValidator) {

    operator fun invoke(email: String): Boolean = emailPatternValidator.isValidEmailPattern(email)
}
