package com.artemissoftware.tasky.navigation

sealed class Destination {

    object Login : Destination()

    object Register : Destination()

    object Agenda : Destination()
}
