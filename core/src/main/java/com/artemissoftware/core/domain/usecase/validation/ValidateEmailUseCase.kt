package com.artemissoftware.core.domain.usecase.validation

import com.artemissoftware.core.domain.validators.EmailPatternValidator
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(private val emailPatternValidator: EmailPatternValidator) {

    operator fun invoke(email: String): Boolean = emailPatternValidator.isValidEmailPattern(email)
}
