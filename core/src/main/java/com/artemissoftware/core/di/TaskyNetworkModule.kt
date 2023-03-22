package com.artemissoftware.core.di

import com.artemissoftware.core.BuildConfig
import com.artemissoftware.core.util.interceptors.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskyNetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(ApiKeyInterceptor())
            // .addInterceptor(JwtInterceptor())
            .readTimeout(BuildConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}
