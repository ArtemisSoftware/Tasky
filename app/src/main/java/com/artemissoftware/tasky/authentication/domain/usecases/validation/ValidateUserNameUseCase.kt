package com.artemissoftware.tasky.authentication.domain.usecases.validation

class ValidateUserNameUseCase {

    operator fun invoke(name: String): Boolean  {

        if (name.isEmpty() || name.isBlank()) {
            return false
        }

        return USER_NAME_REGEX.toRegex().matches(name)
    }

    companion object{

        private const val USER_NAME_REGEX = "^[A-Za-z0-9\\s\\S]{4,50}\$" // TODO : min and max from build config
    }
}