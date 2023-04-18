package com.artemissoftware.core.domain.validators

interface EmailPatternValidator {
    fun isValidEmailPattern(email: String): Boolean
}
