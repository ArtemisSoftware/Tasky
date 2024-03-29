package com.artemissoftware.core.di

import com.artemissoftware.core.BuildConfig
import com.artemissoftware.core.data.remote.api.TaskyAuthenticationApi
import com.artemissoftware.core.domain.usecase.DeleteAllUserDataUseCase
import com.artemissoftware.core.domain.usecase.GetUserUseCase
import com.artemissoftware.core.util.interceptors.ApiKeyInterceptor
import com.artemissoftware.core.util.interceptors.JwtInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskyNetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(getUserUseCase: GetUserUseCase, deleteAllUserDataUseCase: DeleteAllUserDataUseCase): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(JwtInterceptor(getUserUseCase = getUserUseCase, deleteAllUserDataUseCase = deleteAllUserDataUseCase))
            .readTimeout(BuildConfig.READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskyAuthenticationApi(retrofit: Retrofit): TaskyAuthenticationApi {
        return retrofit
            .create(TaskyAuthenticationApi::class.java)
    }
}
