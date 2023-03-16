package com.artemissoftware.tasky.authentication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.events.UiEvent
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
    private val validateUserNameUseCase: ValidateUserNameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun onTriggerEvent(event: RegisterEvents){
        when(event){
            RegisterEvents.PopBackStack -> { /* TODO : sendUiEvent(UiEvent.PopBackStack) */ }
            RegisterEvents.Register -> { register() }
            is RegisterEvents.ValidateEmail -> { validateEmail(email = event.email) }
            is RegisterEvents.ValidateName -> { validateName(name = event.name) }
            is RegisterEvents.ValidatePassword -> { validatePassword(password = event.password) }
        }
    }

    private fun validateEmail(email: String) {

        _state.update {
            it.copy(
                email = email,
                emailValidationStateType = TaskyTextFieldValidationStateType.getStateType(validateEmailUseCase(email))
            )
        }
    }

    private fun validateName(name: String) {

        _state.update {
            it.copy(
                name = name,
                nameValidationStateType = TaskyTextFieldValidationStateType.getStateType(validateUserNameUseCase(name))
            )
        }
    }

    private fun validatePassword(password: String) {

        _state.update {
            it.copy(
                password = password,
                passwordValidationStateType = TaskyTextFieldValidationStateType.getStateType(validatePasswordUseCase(password))
            )
        }
    }

    private fun register() {

        with(_state.value){

            if(allRegisterFieldsValid()){

                _state.update {
                    it.copy(isLoading = true)
                }

                viewModelScope.launch {

                    val result = registerUserUseCase(email = email, password = password, fullName = name)

                    when (result) {

                        is Resource.Success -> {
                            // TODO : send uiEvent to navigate to login + close register screen
                        }
                        is Resource.Error -> {
                            result.exception?.let {  }
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
}