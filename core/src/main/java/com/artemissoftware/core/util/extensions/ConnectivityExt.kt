package com.artemissoftware.core.util.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.artemissoftware.core.presentation.composables.connectivity.models.TaskyConnectionState
import com.artemissoftware.core.util.NetworkUtil.networkCallback
import com.artemissoftware.core.util.NetworkUtil.getCurrentConnectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

//val Context.connectivityManager get(): ConnectivityManager {
//    return getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//}

val Context.currentConnectivityState: TaskyConnectionState
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow(): Flow<TaskyConnectionState> = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    trySend(getCurrentConnectivityState(connectivityManager))

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}
