package com.mike.core.presentation.utils.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber

@Composable
fun <T> ObserveUiAction(
    flow: Flow<T>,
    onUiAction: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { event ->
                    onUiAction(event)
                }
            }
        }
    }
}

@Composable
fun LogCurrentScreen(screenName: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Firebase.crashlytics.log("open screen $screenName")
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                val string = "open screen $screenName"
                Timber.tag("LogCurrentScreen").d(string)
                Firebase.crashlytics.log(string)
                FirebaseCrashlytics.getInstance().log("FirebaseCrashlytics $string")
            }
        }
    }
    DisposableEffect(lifecycleOwner.lifecycle) {
        onDispose {
            val string = "close screen $screenName"
            Timber.tag("LogCurrentScreen").d(string)
            Firebase.crashlytics.log(string)
        }
    }
}