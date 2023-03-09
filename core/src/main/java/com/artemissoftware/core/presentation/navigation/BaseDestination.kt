package com.artemissoftware.core.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.artemissoftware.core.presentation.navigation.models.CustomNavigationArgument


abstract class BaseDestination(
    private val route: String,
    protected val customArguments: List<CustomNavigationArgument> = emptyList()
) {
    fun getFullRoute(): String {
        return if(customArguments.isEmpty()) route else routeWithArguments
    }

    private val routeWithArguments: String = buildString {
        append(route)
        customArguments.forEachIndexed { index, custom ->
            val symbol = if (index == 0) "?" else "&"
            append("$symbol${custom.key}={${custom.key}}")
        }
    }

    val arguments: List<NamedNavArgument> = customArguments.map {
        navArgument(it.key) {
            type = it.type
            nullable = it.nullable
        }
    }

    fun withArgs(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEachIndexed { index, arg ->
                val symbol = if (index == 0) "?" else "&"
                append("$symbol${customArguments[index].key}=$arg")
            }
        }
    }

    protected fun addQueryToRoute(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEachIndexed { index, arg ->
                val symbol = if (index == 0) "?" else "&"
                if(arg != null) {
                    append("$symbol${arg}")
                }
            }
        }
    }

}