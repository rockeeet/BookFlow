
package com.example.bookflow.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.AppThemeMode
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.BookFlowTheme

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings
}

@Composable
fun SettingsScreen(
    themeMode: AppThemeMode,
    appVersion: String,
    onThemeChange: (AppThemeMode) -> Unit,
    onAboutClick: () -> Unit,
    onLicensesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.settings),
                canNavigateBack = false,
                actions = {},
                navigateUp = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // Appearance
            SettingsSection(
                title = stringResource(R.string.appearance)
            ) {
                SettingsCard {
                    SettingsOptionRow(
                        title = stringResource(R.string.system_default),
                        description = stringResource(R.string.system_default_description),
                        selected = themeMode == AppThemeMode.SYSTEM,
                        onClick = { onThemeChange(AppThemeMode.SYSTEM) }
                    )

                    SettingsOptionRow(
                        title = stringResource(R.string.light_theme),
                        selected = themeMode == AppThemeMode.LIGHT,
                        onClick = { onThemeChange(AppThemeMode.LIGHT) }
                    )

                    SettingsOptionRow(
                        title = stringResource(R.string.dark_theme),
                        selected = themeMode == AppThemeMode.DARK,
                        onClick = { onThemeChange(AppThemeMode.DARK) }
                    )
                }
            }

            // About
            SettingsSection(
                title = stringResource(R.string.about)
            ) {
                SettingsCard {
                    AboutSettingsRow(
                        title = stringResource(R.string.app_version),
                        value = appVersion
                    )

                    AboutSettingsRow(
                        title = stringResource(R.string.about_bookflow),
                        onClick = onAboutClick
                    )

                    AboutSettingsRow(
                        title = stringResource(R.string.open_source_licenses),
                        onClick = onLicensesClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsCard(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun AboutSettingsRow(
    title: String,
    value: String? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    BookFlowTheme(darkTheme = false) {
        SettingsScreen(
            themeMode = AppThemeMode.SYSTEM,
            appVersion = "1.0.0",
            onThemeChange = {},
            onAboutClick = {},
            onLicensesClick = {}
        )
    }
}
