package com.mike.vehicles.presentation.ui.vehicledetails.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mike.core.domain.utils.DateFormats
import com.mike.core.presentation.theme.SPACE_SCREEN_H
import com.mike.core.presentation.theme.SPACING_LARGE
import com.mike.core.presentation.theme.SPACING_MEDIUM
import com.mike.core.presentation.theme.SPACING_MEDIUM_PLUS
import com.mike.core.presentation.theme.SPACING_SMALL_PLUS
import com.mike.core.presentation.views.Separator
import com.mike.core.presentation.views.SeparatorModifier
import com.mike.domian.vehicles.models.LifeSpan
import com.mike.domian.vehicles.models.VehiclePart
import com.mike.domian.vehicles.models.VehiclePartStatus
import com.mike.resources.R
import com.mike.vehicles.presentation.ui.vehicledetails.DetailsItem
import com.mike.vehicles.presentation.utils.partStatusColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt
@Composable
fun ItemVehiclePart(
    item: DetailsItem.PartItem,
    dateFormat: SimpleDateFormat,
    onRenewPart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = SPACE_SCREEN_H,
                end = SPACE_SCREEN_H,
                bottom = SPACING_LARGE
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
        ) {
            CardContent(
                item = item,
                dateFormat = dateFormat,
                onRenewPart
            )
        }
    }
}

@Composable
fun CardContent(
    item: DetailsItem.PartItem,
    dateFormat: SimpleDateFormat,
    onRenewPart: () -> Unit
) {
    val part = item.part
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = part.partName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SPACING_MEDIUM_PLUS, end = SPACING_MEDIUM_PLUS, top = SPACING_MEDIUM_PLUS)
        )
        if (!part.supplier.isNullOrEmpty()) {
            Text(
                text = stringResource(R.string.supplier_x, (part.supplier ?: "")),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = SPACING_MEDIUM_PLUS, end = SPACING_MEDIUM_PLUS, top = SPACING_SMALL_PLUS)
            )
        }
        //Separator
        Separator(
            modifier = SeparatorModifier,
            paddingValues = PaddingValues(top = SPACING_MEDIUM)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SPACING_MEDIUM_PLUS, end = SPACING_MEDIUM_PLUS, top = SPACING_MEDIUM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_calendar_month_24),
                contentDescription = null,
                modifier = Modifier.padding(end = SPACING_SMALL_PLUS)
            )
            Text(
                text = "${dateFormat.format(part.deploymentDate)} - ${dateFormat.format(part.expiryDate)}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SPACING_MEDIUM_PLUS, end = SPACING_MEDIUM_PLUS, top = SPACING_SMALL_PLUS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.outline_road_24),
                contentDescription = null,
                modifier = Modifier.padding(end = SPACING_SMALL_PLUS)
            )
            Text(
                text = "${part.deploymentKM.roundToInt()} - ${part.expiryKm.roundToInt()} KM",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SPACING_MEDIUM_PLUS, end = SPACING_MEDIUM_PLUS, top = SPACING_SMALL_PLUS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val tintColor = partStatusColor(item.partStatus)
            Image(
                painter = painterResource(R.drawable.baseline_build_24),
                contentDescription = null,
                modifier = Modifier.padding(end = SPACING_SMALL_PLUS),
                colorFilter = ColorFilter.tint(tintColor)
            )
            Text(
                text = when(item.partStatus){
                    VehiclePartStatus.OK ->
                        stringResource(R.string.part_status_ok)
                    VehiclePartStatus.EXPIRED ->
                        stringResource(R.string.part_status_expired)
                    VehiclePartStatus.NEAR_EXPIRATION ->
                        stringResource(R.string.part_status_near_expiry)
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = tintColor
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SPACING_MEDIUM_PLUS, end = SPACING_MEDIUM_PLUS, top = SPACING_SMALL_PLUS, bottom = SPACING_MEDIUM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (part.price != null) {
                Image(
                    painter = painterResource(R.drawable.baseline_money_24),
                    contentDescription = null,
                    modifier = Modifier.padding(all = SPACING_SMALL_PLUS)
                )
                Text(
                    text = part.price.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1.0f)
                )
            } else {
                Box(Modifier.weight(1.0f))
            }

            Image(
                painter = painterResource(R.drawable.baseline_autorenew_24),
                contentDescription = null,
                modifier = Modifier
                    .clickable{
                        onRenewPart()
                    }
                    .padding(end = SPACING_SMALL_PLUS)

            )
        }
    }
}

@Preview
@Composable
fun ItemVehiclePartPreview() {
    val dateFormat = SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault())
    val part = DetailsItem.PartItem(
        part = VehiclePart(
            partName = "Brake Pads",
            supplier = "Auto Parts Co.",
            deploymentDate = Date(),
            deploymentKM = 10040.0,
            lifeSpan = LifeSpan(km = 5000.0, months = 24),
            price = 150.0,
        ),
        partStatus = VehiclePartStatus.NEAR_EXPIRATION
    )
    ItemVehiclePart(item = part, dateFormat = dateFormat, onRenewPart = {})
}