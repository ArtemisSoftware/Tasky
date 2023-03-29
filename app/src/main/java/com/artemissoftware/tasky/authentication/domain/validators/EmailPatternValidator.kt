package com.artemissoftware.tasky.authentication.domain.validators

interface EmailPatternValidator {
    fun isValidEmailPattern(email: String): Boolean
}
