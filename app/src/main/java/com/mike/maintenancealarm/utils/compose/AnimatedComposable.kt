package com.mike.maintenancealarm.utils.compose

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

enum class NavAnimationType {
    SLIDE_IN_FROM_RIGHT,
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
        NavAnimationType.SLIDE_IN_FROM_RIGHT -> slideInHorizontally()
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideInVertically(initialOffsetY = { it })
        NavAnimationType.NONE -> EnterTransition.None
    }
}

fun exitTransition(
    navAnimationType: NavAnimationType
): ExitTransition {
    return when (navAnimationType) {
        NavAnimationType.SLIDE_IN_FROM_RIGHT -> slideOutHorizontally()
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideOutVertically(targetOffsetY = { -it })
        NavAnimationType.NONE -> ExitTransition.None
    }
}

fun popEnterTransition(
    navAnimationType: NavAnimationType
): EnterTransition {
    return when (navAnimationType) {
        NavAnimationType.SLIDE_IN_FROM_RIGHT -> slideInHorizontally { it }
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideInVertically(initialOffsetY = { -it })
        NavAnimationType.NONE -> EnterTransition.None
    }
}

fun popExitTransition(
    navAnimationType: NavAnimationType
): ExitTransition {
    return when (navAnimationType) {
        NavAnimationType.SLIDE_IN_FROM_RIGHT -> slideOutHorizontally { it }
        NavAnimationType.SLIDE_IN_FROM_BOTTOM -> slideOutVertically(targetOffsetY = { it })
        NavAnimationType.NONE -> ExitTransition.None
    }
}
