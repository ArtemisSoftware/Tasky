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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artemissoftware.tasky.authentication.presentation.login.ManageUIEvents
import com.artemissoftware.tasky.destinations.LoginScreenDestination
import com.artemissoftware.tasky.navigation.Destination
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
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
private fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

//    TaskyScaffold(
//        taskyScaffoldState = state.scaffoldState,
//        backgroundColor = Green,
//        content = {
//            Box(modifier = Modifier.fillMaxSize()) {
//                TaskyIcon(
//                    modifier = Modifier.align(Alignment.Center),
//                    color = White,
//                    size = 140.dp,
//                    icon = R.drawable.ic_tasky_logo,
//                )
//            }
//        },
//    )

    when (state.destinationAfterSplash) {
        Destination.Agenda -> {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
            )
        }
        Destination.Login -> {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = LoginScreenDestination,
            )
        }
        else -> {}
    }

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        showDialog = {
            state.scaffoldState.showDialog(it)
        },
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
