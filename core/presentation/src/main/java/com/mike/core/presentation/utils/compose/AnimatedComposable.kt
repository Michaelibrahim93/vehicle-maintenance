package com.mike.core.presentation.utils.compose

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

enum class NavAnimationType {
    SLIDE_IN_HORIZONTALLY,
    SLIDE_IN_FROM_BOTTOM,
    NONE
}

inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    navAnimationType: NavAnimationType,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = { enterTransition(navAnimationType) },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { popEnterTransition(navAnimationType) },
        popExitTransition = { popExitTransition(navAnimationType) },
    ) {
        content(it)
    }
}

fun enterTransition(
    navAnimationType: NavAnimationType
): EnterTransition {
    return when (navAnimationType) {
        NavAnimationType.SLIDE_IN_HORIZONTALLY -> slideInHorizontally()
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideInVertically(initialOffsetY = { it })
        NavAnimationType.NONE -> EnterTransition.None
    }
}

fun popEnterTransition(
    navAnimationType: NavAnimationType
): EnterTransition {
    return when (navAnimationType) {
        NavAnimationType.SLIDE_IN_HORIZONTALLY -> slideInHorizontally { it }
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideInVertically(initialOffsetY = { -it })
        NavAnimationType.NONE -> EnterTransition.None
    }
}

fun popExitTransition(
    navAnimationType: NavAnimationType
): ExitTransition {
    return when (navAnimationType) {
        NavAnimationType.SLIDE_IN_HORIZONTALLY -> slideOutHorizontally { -it }
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideOutVertically(targetOffsetY = { it })
        NavAnimationType.NONE -> ExitTransition.None
    }
}
