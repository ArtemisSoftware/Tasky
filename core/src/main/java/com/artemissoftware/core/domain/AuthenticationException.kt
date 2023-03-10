package com.artemissoftware.core.domain

sealed class AuthenticationException : ValidationException(){

    object LoginError : AuthenticationException()
    object RegisterError : AuthenticationException()
}
