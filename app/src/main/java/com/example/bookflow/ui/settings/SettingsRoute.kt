package com.example.bookflow.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider

@Composable
fun SettingsRoute(
    factory: ViewModelProvider.Factory,
    onAboutClick: () -> Unit,
    onLicensesClick: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel(
        factory = factory
    )

    val themeMode by viewModel.themeMode.collectAsState()

    SettingsScreen(
        themeMode = themeMode,
        appVersion = "1.0.0",
        onThemeChange = viewModel::setThemeMode,
        onAboutClick = onAboutClick,
        onLicensesClick = onLicensesClick
    )
}