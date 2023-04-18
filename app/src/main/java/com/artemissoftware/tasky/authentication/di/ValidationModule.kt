package com.artemissoftware.tasky.authentication.di

import com.artemissoftware.core.data.validators.EmailPatternValidatorImpl
import com.artemissoftware.core.domain.validators.EmailPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidationModule {

    @Provides
    @Singleton
    fun provideEmailPatternValidator(): EmailPatternValidator {
        return EmailPatternValidatorImpl()
    }
}
