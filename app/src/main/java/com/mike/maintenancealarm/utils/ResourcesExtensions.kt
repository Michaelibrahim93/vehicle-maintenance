package com.mike.maintenancealarm.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun Int.stringRes(): String {
    return stringResource(id = this)
}