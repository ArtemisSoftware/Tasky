package com.artemissoftware.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.core.presentation.composables.dialog.TaskyDialog
import com.artemissoftware.core.util.interceptors.logOutState
import com.artemissoftware.tasky.destinations.LoginScreenDestination
import com.artemissoftware.tasky.destinations.RegisterScreenDestination
import com.artemissoftware.tasky.ui.theme.TaskyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.state.value.showSplash
            }
        }
        setContent {
            TaskyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val navController = rememberNavController()
                    MainScreen(
                        events = viewModel::onTriggerEvent,
                        state = viewModel.state.collectAsStateWithLifecycle().value,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun MainScreen(
    events: (MainEvents) -> Unit,
    navController: NavHostController,
    state: MainState
) {

    state.destinationAfterSplash?.let {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            startRoute = it,
            navController = navController,
        )
        navController
        LogOut(navController = navController, events)
    }

    state.taskyDialogState.dialog.value?.let {
        TaskyDialog(
            taskyDialogType = it,
            onDialogDismiss = {
                state.taskyDialogState.closeDialog()
                navigateToLogin(navController)
            },
        )
    }
}

@Composable
fun LogOut(navController: NavHostController, events: (MainEvents) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        logOutState.collect {
            if (showSessionExpiredDialog(navController)) {
                events(MainEvents.ShowUserLoggedOutDialog)
            }
        }
    }
}

private fun showSessionExpiredDialog(navController: NavHostController): Boolean{
    return navController.currentDestination?.let { destination ->

        if(destination.route == LoginScreenDestination().route
            || destination.route == RegisterScreenDestination().route){
            false
        }
        else true
    } ?: true
}

private fun navigateToLogin(navController: NavHostController){
    navController.navigate(
        route = LoginScreenDestination().route,
        navOptions = NavOptions.Builder()
            .setPopUpTo(navController.currentDestination?.route, inclusive = true)
            .build(),
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskyTheme {
        Greeting("Android")
    }
}
