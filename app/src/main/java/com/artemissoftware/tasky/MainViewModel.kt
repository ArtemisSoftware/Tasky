package com.artemissoftware.tasky

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.AuthenticationException
import com.artemissoftware.core.domain.ValidationException
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogOptions
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialogType
import com.artemissoftware.core.presentation.events.TaskyUiEventViewModel
import com.artemissoftware.core.presentation.mappers.toUiText
import com.artemissoftware.core.util.UiText
import com.artemissoftware.tasky.authentication.domain.usecases.AuthenticateUseCase
import com.artemissoftware.tasky.destinations.AgendaScreenDestination
import com.artemissoftware.tasky.destinations.LoginScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.artemissoftware.core.R as CoreR

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        authenticate()
    }

    fun onTriggerEvent(event: MainEvents) {
        when (event) {
            MainEvents.ShowUserLoggedOutDialog -> { showUserLoggedOutDialog() }
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            val result = authenticateUseCase()

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(showSplash = false, destinationAfterSplash = AgendaScreenDestination)
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(showSplash = false, destinationAfterSplash = LoginScreenDestination)
                    }
                }
                else -> Unit
            }
        }
    }

    private fun showUserLoggedOutDialog(){
        viewModelScope.launch {
            _state.value.taskyDialogState.showDialog(getDialogData(ex = AuthenticationException.SessionExpired))
        }
    }

    private fun getDialogData(ex: ValidationException): TaskyDialogType {
        return TaskyDialogType.Error(
            title = UiText.StringResource(R.string.app_name),
            description = ex.toUiText(),
            dialogOptions = TaskyDialogOptions.SingleOption(
                confirmationText = UiText.StringResource(CoreR.string.ok),
            ),
        )
    }
}
