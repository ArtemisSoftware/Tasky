package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.BuildConfig
import com.artemissoftware.core.util.annotations.NoApiKeyHeaderRequest
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

class HeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        original.tag(Invocation::class.java)?.let { invocation ->
            if (invocation.method().isAnnotationPresent(NoApiKeyHeaderRequest::class.java)) {
                return chain.proceed(original)
            } else {
                val request: Request = original.newBuilder()
                    .header(HEADER_X_API_KEY, BuildConfig.API_KEY)
                    .method(original.method, original.body)
                    .build()

                return chain.proceed(request)
            }
        } ?: kotlin.run {
            return chain.proceed(original)
        }
    }

    companion object {
        private const val HEADER_X_API_KEY = "x-api-key"
    }
}
