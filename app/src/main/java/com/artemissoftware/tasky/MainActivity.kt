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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.artemissoftware.tasky.authentication.presentation.NavGraphs
import com.artemissoftware.tasky.authentication.presentation.destinations.LoginScreenDestination
import com.artemissoftware.tasky.ui.theme.TaskyTheme
import dagger.hilt.android.AndroidEntryPoint
import com.ramcosta.composedestinations.DestinationsNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            TaskyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
//                    if (!viewModel.isLoading.collectAsState().value) {
//                        DestinationsNavHost(
//                            navGraph = NavGraphs.root,
//                            startRoute = LoginScreenDestination,
//                        )
//                    }
                }
            }
        }
    }
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
