package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.BuildConfig
import com.artemissoftware.core.util.annotations.NoHeaderRequest
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

class HeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        original.tag(Invocation::class.java)?.let { invocation ->
            if (invocation.method().isAnnotationPresent(NoHeaderRequest::class.java)) {
                return chain.proceed(original)
            } else {
                val request: Request = original.newBuilder()
                    .header("x-api-key", BuildConfig.API_KEY)
                    .method(original.method, original.body)
                    .build()

                return chain.proceed(request)
            }
        } ?: kotlin.run {
            return chain.proceed(original)
        }
    }
}
