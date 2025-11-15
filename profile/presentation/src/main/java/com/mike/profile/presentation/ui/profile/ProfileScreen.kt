package com.mike.profile.presentation.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mike.core.presentation.theme.SPACING_LARGE
import com.mike.profile.domain.Profile
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileScreenComposable(
    rootNavController: NavController,
    navController: NavController
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(ProfileUiState())
    ProfileScreen(uiState)
}

@Composable
fun ProfileScreen(uiState: ProfileUiState) {
    if (uiState.profile == null) {
        Box() {

        }
    } else {
        ProfileContent(uiState.profile)
    }
}

@Composable
fun ProfileContent(profile: Profile) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val dateFormat = rememberSaveable {
            SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
        }

        Text(
            text = profile.name,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = dateFormat.format(profile.birthDate),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = SPACING_LARGE)
        )
    }
}

@Preview
@Composable
fun Preview_ProfileScreen() {
    ProfileScreen(
        uiState = ProfileUiState()
    )
}