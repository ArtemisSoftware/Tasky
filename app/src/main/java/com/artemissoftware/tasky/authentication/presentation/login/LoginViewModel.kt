package com.artemissoftware.tasky.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.composables.textfield.TaskyTextFieldValidationStateType
import com.artemissoftware.core.presentation.events.UiEvent
import com.artemissoftware.core.presentation.mappers.toUiText
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.R
import com.artemissoftware.core.R as CoreR
import com.artemissoftware.tasky.authentication.domain.usecases.LoginUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidateEmailUseCase
import com.artemissoftware.tasky.authentication.domain.usecases.validation.ValidatePasswordUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onTriggerEvent(event: LoginEvents){
        when(event){
            LoginEvents.Login -> { login() }
            LoginEvents.SignUp -> { signUp() }
            is LoginEvents.ValidateEmail -> { validateEmail(email = event.email) }
            is LoginEvents.ValidatePassword ->  { validatePassword(password = event.password) }
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

    private fun validatePassword(password: String) {

        _state.update {
            it.copy(
                password = password,
                emailValidationStateType = TaskyTextFieldValidationStateType.getStateType(validatePasswordUseCase(password))
            )
        }
    }

    private fun signUp(){

        // TODO : send uiEvent to navigate to sign up screen
        //// sendUiEvent(UiEvent.Navigate)
    }

    private fun login() {

        with(_state.value){

            if(emailValidationStateType == TaskyTextFieldValidationStateType.VALID && passwordValidationStateType == TaskyTextFieldValidationStateType.VALID){

                viewModelScope.launch {

                    val result = loginUseCase(email = email, password = password)

                    when (result) {

                        is Resource.Success -> {
                            // TODO : send uiEvent to navigate to agenda + close login screen
                        }
                        is Resource.Error -> {
                            result.exception?.let {
                                _uiEvent.send(
                                    UiEvent.ShowDialog(getDialogData(ex = it, reloadEvent = { login() }))
                                )
                            }
                        }
                        is Resource.Loading -> Unit
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
                cancelText = UiText.StringResource(CoreR.string.cancel)
            )
        )
    }
}