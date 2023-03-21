package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.domain.usecase.GetUserUseCase
import com.artemissoftware.core.util.annotations.NeedsTokenHeaderRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

// TODO: add @inject anotation on constructor (when hilt is implemented) to inject usecase
class JwtInterceptor constructor(private val getUserUseCase: GetUserUseCase) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        original.tag(Invocation::class.java)?.let { invocation ->
            if (invocation.method().isAnnotationPresent(NeedsTokenHeaderRequest::class.java)) {
                val request: Request = original.newBuilder()
                    .header(HEADER_AUTHORIZATION, "$BEARER ${getToken()}")
                    .method(original.method, original.body)
                    .build()

                return chain.proceed(request)
            } else {
                return chain.proceed(original)
            }
        } ?: run {
            return chain.proceed(original)
        }
    }

    private fun getToken(): String {
        var token = ""
        runBlocking {
            token = getUserUseCase.invoke().first().token
        }

        return token
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
