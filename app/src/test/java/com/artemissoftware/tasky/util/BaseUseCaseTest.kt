package com.artemissoftware.tasky.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class BaseUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()
}