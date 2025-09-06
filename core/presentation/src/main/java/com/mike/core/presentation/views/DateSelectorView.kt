package com.mike.core.presentation.views

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mike.core.domain.utils.dayOfMonth
import com.mike.core.domain.utils.month
import com.mike.core.domain.utils.toCalendar
import com.mike.core.domain.utils.year
import com.mike.core.domain.utils.DateFormats
import com.mike.core.domain.utils.DateFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateSelectorView(
    modifier: Modifier,
    value: Date? = null,
    onValueChange: (Date) -> Unit,
    label: String = "",
    isError: Boolean = false,
    supportingText: String? = null,
) {
    val dateFormat = remember { SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault()) }
    val context = LocalContext.current
    InputFieldView(
        modifier = modifier,
        value = value?.let { dateFormat.format(it) } ?: "",
        onValueChange = { dateString ->
            val parsedDate = dateFormat.parse(dateString)
            if (parsedDate != null) {
                onValueChange(parsedDate)
            }
        },
        label = label,
        singleLine = true,
        maxLines = 1,
        isError = isError,
        supportingText = supportingText,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        readOnly = true,
        onClick = { showDatePicker(
            context = context,
            initialDate = value ?: Date(),
            onDateSelected = onValueChange
        ) }
    )
}

private fun showDatePicker(
    context: Context,
    initialDate: Date,
    onDateSelected: (Date) -> Unit
) {
    val initDate = initialDate.toCalendar()
    DatePickerDialog(context,
        { _, year, month, dayOfMonth ->
            val selectedDate = DateFactory.createDate(year, month, dayOfMonth)
            onDateSelected(selectedDate)
        }, initDate.year, initDate.month, initDate.dayOfMonth
    ).show()
}

@Preview
@Composable
fun DateSelectorViewPreview() {
    DateSelectorView(
        modifier = Modifier,
        value = Date(),
        onValueChange = {},
        label = "Select Date",
        isError = false,
        supportingText = "Please select a date"
    )
}
