package com.artemissoftware.tasky.authentication.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.tasky.authentication.domain.usecases.AuthenticateUseCase
import kotlinx.coroutines.launch


class SplashViewModel constructor(
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel() {

    init{
        authenticate()
    }

    private fun authenticate() {

        viewModelScope.launch {

            val result = authenticateUseCase()

            when (result) {

                is Resource.Success -> {
                    // TODO : send uiEvent to navigate to agenda + close splash screen
                }
                is Resource.Error -> {
                    // TODO : send uiEvent to navigate to login + close splash screen
                }
                is Resource.Loading -> Unit
            }
        }

    }
}