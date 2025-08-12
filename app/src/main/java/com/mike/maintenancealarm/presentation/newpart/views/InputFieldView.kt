package com.mike.maintenancealarm.presentation.newpart.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InputFieldView(
    modifier: Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    label: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val clickableModifier = if (onClick != null)
         modifier.clickable { onClick() }
    else
        modifier

    TextField(
        modifier = clickableModifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        singleLine = singleLine,
        isError = isError,
        readOnly = readOnly,
        supportingText = {
            if (!supportingText.isNullOrEmpty()) {
                Text(text = supportingText, color = MaterialTheme.colorScheme.error)
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

