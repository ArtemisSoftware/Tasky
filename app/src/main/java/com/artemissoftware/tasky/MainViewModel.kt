package com.artemissoftware.tasky

import androidx.lifecycle.viewModelScope
import com.artemissoftware.core.domain.models.Resource
import com.artemissoftware.core.presentation.TaskyUiEventViewModel
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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
) : TaskyUiEventViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        authenticate()
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
                is Resource.Loading -> Unit
            }
        }
    }
}
