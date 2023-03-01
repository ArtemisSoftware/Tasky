package com.artemissoftware.tasky.data.remote.exceptions

object NetworkErrors {

    val UNKNOWN_HOST = 1 to "UnknownHostException"
    val UNKNOWN_API = 2 to "An error has occurred"
    val GENERIC_API_ERROR = 3 to "An error has occurred"
}