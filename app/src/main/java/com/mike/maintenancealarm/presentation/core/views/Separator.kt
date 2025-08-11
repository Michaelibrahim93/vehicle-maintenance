package com.mike.maintenancealarm.presentation.core.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mike.maintenancealarm.presentation.theme.SPACING_MEDIUM

val SeparatorModifier = Modifier
    .padding(top = SPACING_MEDIUM)
    .fillMaxWidth()
    .height(1.dp)
    .background(color = Color.LightGray)

@Composable
fun Separator(
    modifier: Modifier,
    paddingValues: PaddingValues
) {
    Box(
        modifier = modifier
            .then(Modifier.padding(paddingValues))
    )
}