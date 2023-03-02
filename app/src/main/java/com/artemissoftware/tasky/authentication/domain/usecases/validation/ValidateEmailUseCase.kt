package com.artemissoftware.tasky.authentication.domain.usecases.validation

class ValidateEmailUseCase {

    operator fun invoke(email: String): Boolean  {

        if (email.isEmpty() || email.isBlank()) {
            return false
        }

        return EMAIL_REGEX.toRegex().matches(email)
    }

    companion object{

        private const val EMAIL_REGEX = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)\$"
    }
}