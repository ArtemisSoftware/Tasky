package com.artemissoftware.core.util.interceptors

import com.artemissoftware.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("x-api-key", BuildConfig.API_KEY)
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}
