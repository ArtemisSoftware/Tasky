package com.artemissoftware.tasky.authentication.di

import com.artemissoftware.tasky.BuildConfig
import com.artemissoftware.tasky.authentication.data.remote.TaskyAuthenticationApi
import com.artemissoftware.tasky.authentication.data.remote.source.AuthenticationApiSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTaskyAuthenticationApi(okHttpClient: OkHttpClient): TaskyAuthenticationApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TaskyAuthenticationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticationApiSource(taskyAuthenticationApi: TaskyAuthenticationApi): AuthenticationApiSource {
        return AuthenticationApiSource(taskyAuthenticationApi)
    }
}
