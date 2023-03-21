package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

// TODO: add @inject anotation on constructor (when hilt is implemented) to inject usecase
class JwtInterceptor constructor(private val getUserUseCase: GetUserUseCase) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var token = ""
        runBlocking {
            token = getUserUseCase.invoke().first().token
        }

        val request = chain.request().newBuilder()

        if (token.isNotEmpty()) {
            request.addHeader(HEADER_AUTHORIZATION, "$BEARER $token")
        }
        return chain.proceed(request.build())
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
