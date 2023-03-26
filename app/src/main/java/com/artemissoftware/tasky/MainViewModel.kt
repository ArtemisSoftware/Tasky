package com.artemissoftware.tasky

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
import com.artemissoftware.tasky.authentication.domain.usecases.AuthenticateUseCase
import com.artemissoftware.tasky.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

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
                    result.exception?.let { exception ->
                        when (exception) {
                            AuthenticationException.UserNotAuthenticated -> {
                                delay(500)
                                _state.update {
                                    it.copy(showSplash = false, destinationAfterSplash = Destination.Login)
                                }
                            }
                            else -> {
                                _state.update { it.copy(showSplash = false) }
                                sendUiEvent(UiEvent.ShowDialog(getDialogData(ex = exception, reloadEvent = { authenticate() })))
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
            dialogOptions = TaskyDialogOptions.SingleOption(
                confirmationText = UiText.StringResource(R.string.retry),
                confirmation = {
                    reloadEvent.invoke()
                },
            ),
        )
    }
}