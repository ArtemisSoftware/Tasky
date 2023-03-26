package com.artemissoftware.tasky.authentication.data.validators

import android.util.Patterns
import com.artemissoftware.tasky.authentication.domain.validators.EmailPatternValidator

class EmailPatternValidatorImpl : EmailPatternValidator {

    override fun isValidEmailPattern(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
