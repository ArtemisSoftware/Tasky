package com.artemissoftware.tasky.authentication.presentation.register

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
import com.artemissoftware.tasky.authentication.domain.usecases.RegisterUserUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidateEmailUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidatePasswordUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidateUserNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun onTriggerEvent(event: RegisterEvents) {
        when (event) {
            RegisterEvents.PopBackStack -> { popBackStack() }
            RegisterEvents.Register -> { register() }
            is RegisterEvents.ValidateEmail -> { validateEmail(email = event.email) }
            is RegisterEvents.ValidateName -> { validateName(name = event.name) }
            is RegisterEvents.ValidatePassword -> { validatePassword(password = event.password) }
        }
    }

    private fun popBackStack() {
        viewModelScope.launch {
            sendUiEvent(UiEvent.PopBackStack)
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

    private fun validateName(name: String) {
        _state.update {
            it.copy(
                name = name,
                nameValidationStateType = TaskyTextFieldValidationStateType.getStateType(validateUserNameUseCase(name)),
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

    private fun register() {
        if (_state.value.allRegisterFieldsValid()) {
            viewModelScope.launch {
                _state.update {
                    it.copy(isLoading = true)
                }
                with(_state.value) {
                    val result = registerUserUseCase(email = email, password = password, fullName = name)

                    when (result) {
                        is Resource.Success -> {
                            // TODO : send uiEvent to navigate to login + close register screen
                        }
                        is Resource.Error -> {
                            result.exception?.let {
                                sendUiEvent(UiEvent.ShowDialog(getDialogData(ex = it, reloadEvent = { register() })))
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
            title = UiText.StringResource(R.string.register),
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
