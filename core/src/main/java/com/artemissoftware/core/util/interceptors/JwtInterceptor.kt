package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.domain.usecase.GetUserUseCase
import com.artemissoftware.core.util.annotations.NoJWTHeaderRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

private val _logOutState = MutableSharedFlow<Unit>(replay = 3)
val logOutState = _logOutState.asSharedFlow()

class JwtInterceptor @Inject constructor(private val getUserUseCase: GetUserUseCase) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val chain = original.tag(Invocation::class.java)?.let { invocation ->
            if (invocation.method().isAnnotationPresent(NoJWTHeaderRequest::class.java)) {
                chain.proceed(original)
            } else {
                val request: Request = original.newBuilder()
                    .header(HEADER_AUTHORIZATION, "$BEARER ${getToken()}")
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            }
        } ?: kotlin.run {
            chain.proceed(original)
        }

        return chain.also { response ->
            if (response.code == 401) {
                runBlocking {
                    _logOutState.emit(Unit)

                }
            }
        }
    }

    private fun getToken() = runBlocking { getUserUseCase.invoke().first().token }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
