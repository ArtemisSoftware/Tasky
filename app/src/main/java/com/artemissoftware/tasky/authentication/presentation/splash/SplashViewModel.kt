package com.artemissoftware.tasky.authentication.presentation.splash

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.presentation.mappers.toUiText
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.authentication.domain.usecases.AuthenticateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel constructor(
    private val authenticateUseCase: AuthenticateUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    init {
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
                    result.exception?.let {
                        when (it) {
                            AuthenticationException.UserNotAuthenticated -> {
                                // TODO : send uiEvent to navigate to login + close splash screen
                            }
                            else -> {
                                sendUiEvent(UiEvent.ShowDialog(getDialogData(ex = it, reloadEvent = { authenticate() })))
                            }
                        }
                    }
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun getDialogData(ex: ValidationException, reloadEvent: () -> Unit): TaskyDialogType {
        return TaskyDialogType.Error(
            title = UiText.StringResource(R.string.app_name),
            description = ex.toUiText(),
            dialogOptions = TaskyDialogOptions.DoubleOption(
                confirmationText = UiText.StringResource(R.string.retry),
                confirmation = {
                    reloadEvent.invoke()
                },
                cancelText = UiText.StringResource(com.artemissoftware.core.R.string.cancel),
            ),
        )
    }
}
