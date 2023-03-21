package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header(HEADER_X_API_KEY, BuildConfig.API_KEY)
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_X_API_KEY = "x-api-key"
    }
}
