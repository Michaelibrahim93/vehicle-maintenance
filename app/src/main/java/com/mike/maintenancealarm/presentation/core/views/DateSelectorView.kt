package com.mike.maintenancealarm.presentation.core.views

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
import com.mike.maintenancealarm.presentation.core.DateFormats
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.utils.DateFactory
import com.mike.maintenancealarm.utils.extensions.dayOfMonth
import com.mike.maintenancealarm.utils.extensions.month
import com.mike.maintenancealarm.utils.extensions.toCalendar
import com.mike.maintenancealarm.utils.extensions.year
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
    MaintenanceAlarmTheme {
        DateSelectorView(
            modifier = Modifier,
            value = Date(),
            onValueChange = {},
            label = "Select Date",
            isError = false,
            supportingText = "Please select a date"
        )
    }
}
