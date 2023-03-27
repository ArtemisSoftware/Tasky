package com.artemissoftware.tasky.authentication.presentation.login

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.presentation.mappers.toUiText
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.R
import com.artemissoftware.tasky.authentication.domain.usecases.LoginUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidateEmailUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidatePasswordUseCase
import com.artemissoftware.tasky.destinations.AgendaScreenDestination
import com.artemissoftware.tasky.destinations.RegisterScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.artemissoftware.core.R as CoreR

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onTriggerEvent(event: LoginEvents) {
        when (event) {
            LoginEvents.Login -> { login() }
            LoginEvents.SignUp -> { signUp() }
            is LoginEvents.ValidateEmail -> { validateEmail(email = event.email) }
            is LoginEvents.ValidatePassword -> { validatePassword(password = event.password) }
        }
    }

    private fun validateEmail(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailValidationStateType = TaskyTextFieldValidationStateType.getStateType(validateEmailUseCase(email)),
            )
        }
    }

    private fun validatePassword(password: String) {
        _state.update {
            it.copy(
                password = password,
                passwordValidationStateType = TaskyTextFieldValidationStateType.getStateType(validatePasswordUseCase(password)),
            )
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.Navigate(RegisterScreenDestination.route))
        }
    }

    private fun login() {
        with(_state.value) {
            if (allLoginFieldsValid()) {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isLoading = true)
                    }

                    val result = loginUseCase(email = email, password = password)

                    when (result) {
                        is Resource.Success -> {
                            sendUiEvent(UiEvent.NavigateAndPopCurrent(AgendaScreenDestination.route))
                        }
                        is Resource.Error -> {
                            result.exception?.let {
                                sendUiEvent(UiEvent.ShowDialog(getDialogData(ex = it, reloadEvent = { login() })))
                            }
                        }
                        is Resource.Loading -> Unit
                    }

                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun getDialogData(ex: ValidationException, reloadEvent: () -> Unit): TaskyDialogType {
        return TaskyDialogType.Error(
            title = UiText.StringResource(R.string.log_in),
            description = ex.toUiText(),
            dialogOptions = TaskyDialogOptions.DoubleOption(
                confirmationText = UiText.StringResource(R.string.retry),
                confirmation = {
                    reloadEvent.invoke()
                },
                cancelText = UiText.StringResource(CoreR.string.cancel),
            ),
        )
    }
}
