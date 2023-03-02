package com.artemissoftware.core.domain.usecase

class GetUserNameInitialsUseCase {

    operator fun invoke(name: String, limit: Int = 2): String {

        val buffer = StringBuffer()
        name.trim().split(" ").filter {
            it.isNotEmpty()
        }.joinTo(
            buffer = buffer,
            limit = limit,
            separator = "",
            truncated = "",
        ) { word ->
            word.first().uppercase()
        }
        return buffer.toString()
    }
}