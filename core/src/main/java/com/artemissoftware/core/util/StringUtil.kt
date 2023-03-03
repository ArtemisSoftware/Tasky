package com.artemissoftware.core.util

object StringUtil {

    fun getInitials(name: String, limit: Int = 3): String {

        val listOfNames = name
            .trim()
            .split(" ")
            .filter {
                it.isNotEmpty()
            }

        return when (listOfNames.size) {
            1 -> {
                listOfNames[0]
                    .take(2)
                    .uppercase()
            }
            else -> {

                val firstInitial = listOfNames.first().first().toString()
                val lastInitial = listOfNames.last().first().toString()
                (firstInitial + lastInitial).uppercase()
            }
        }
    }
}