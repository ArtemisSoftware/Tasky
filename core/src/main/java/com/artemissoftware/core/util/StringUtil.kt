package com.artemissoftware.core.util

object StringUtil {

    fun getInitials(name: String): String {

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