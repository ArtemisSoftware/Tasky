package com.artemissoftware.core.data.validators

import android.util.Patterns
import com.artemissoftware.core.domain.validators.EmailPatternValidator

class EmailPatternValidatorImpl : EmailPatternValidator {

    override fun isValidEmailPattern(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
