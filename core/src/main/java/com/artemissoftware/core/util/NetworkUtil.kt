package com.artemissoftware.core.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.artemissoftware.core.presentation.composables.connectivity.models.TaskyConnectionState

object NetworkUtil {

    fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): TaskyConnectionState {
        val connected = connectivityManager.allNetworks.any { network ->

            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        }

        return if (connected) TaskyConnectionState.Available else TaskyConnectionState.Unavailable
    }

    fun networkCallback(callback: (TaskyConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                callback(TaskyConnectionState.Available)
            }

            override fun onLost(network: Network) {
                callback(TaskyConnectionState.Unavailable)
            }

            override fun onUnavailable() {
                callback(TaskyConnectionState.Unavailable)
            }
        }
    }
}
