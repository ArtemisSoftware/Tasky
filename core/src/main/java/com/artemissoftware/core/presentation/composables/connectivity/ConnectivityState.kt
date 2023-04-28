package com.artemissoftware.core.presentation.composables.connectivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.artemissoftware.core.presentation.composables.connectivity.models.TaskyConnectionState
import com.artemissoftware.core.util.extensions.currentConnectivityState
import com.artemissoftware.core.util.extensions.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun currentConnectionState(): TaskyConnectionState = with(LocalContext.current) {
    return remember { currentConnectivityState }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun connectivityState(): State<TaskyConnectionState> = with(LocalContext.current) {
    return produceState(initialValue = currentConnectivityState) {
        observeConnectivityAsFlow().collect { value = it }
    }
}
