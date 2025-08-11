package com.mike.maintenancealarm.presentation.vehicledetails.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentation.theme.SPACING_EXTRA_LARGE
import com.mike.maintenancealarm.presentation.theme.SPACING_MEDIUM_PLUS

@Composable
fun ItemEmptyParts() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SPACING_EXTRA_LARGE),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_empty_parts),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = stringResource(R.string.empty_parts_title),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = SPACING_MEDIUM_PLUS)
        )
    }
}

@Preview
@Composable
fun ItemEmptyPartsPreview() {
    MaintenanceAlarmTheme {
        ItemEmptyParts()
    }
}