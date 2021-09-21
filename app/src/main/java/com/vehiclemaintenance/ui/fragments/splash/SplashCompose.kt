package com.vehiclemaintenance.ui.fragments.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vehiclemaintenance.R

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun SplashView() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_directions_car_24),
            contentDescription = "Splash Logo",
            modifier = Modifier
                .size(dimensionResource(R.dimen._200sdp))
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._16sdp)))
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen._100sdp)))
    }
}