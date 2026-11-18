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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.AppThemeMode
import com.example.bookflow.data.model.BookSortOrder
import com.example.bookflow.data.model.LibraryLayout
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.Background
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.SurfaceSecondary

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings
}
@Composable
fun SettingsScreen(
    themeMode: AppThemeMode,
    libraryLayout: LibraryLayout,
    sortOrder: BookSortOrder,
    appVersion: String,
    onThemeChange: (AppThemeMode) -> Unit,
    onLibraryLayoutChange: (LibraryLayout) -> Unit,
    onSortOrderChange: (BookSortOrder) -> Unit,
    onAboutClick: () -> Unit,
    onLicensesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Background,
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

            // Library Preferences
            SettingsSection(
                title = stringResource(R.string.library_preferences)
            ) {
                SettingsCard {
                    Text(
                        text = stringResource(R.string.default_layout),
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryText
                    )

                    SettingsOptionRow(
                        title = stringResource(R.string.grid_view),
                        selected = libraryLayout == LibraryLayout.GRID,
                        onClick = { onLibraryLayoutChange(LibraryLayout.GRID) }
                    )

                    SettingsOptionRow(
                        title = stringResource(R.string.list_view),
                        selected = libraryLayout == LibraryLayout.LIST,
                        onClick = { onLibraryLayoutChange(LibraryLayout.LIST) }
                    )

                    SortOrderSelector(
                        sortOrder = sortOrder,
                        onSortOrderChange = onSortOrderChange
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
        color = SurfaceSecondary
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
private fun SortOrderSelector(
    sortOrder: BookSortOrder,
    onSortOrderChange: (BookSortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text(
            text = stringResource(R.string.default_sort_order),
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryText
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(sortOrder.toStringResource()),
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryText
            )

            Text(
                text = "⌄",
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryGreen
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                BookSortOrder.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(option.toStringResource()))
                        },
                        onClick = {
                            onSortOrderChange(option)
                            expanded = false
                        }
                    )
                }
            }
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
            color = PrimaryText
        )

        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText
            )
        } else {
            Text(
                text = "›",
                style = MaterialTheme.typography.titleMedium,
                color = SecondaryText
            )
        }
    }
}

private fun BookSortOrder.toStringResource(): Int = when (this) {
    BookSortOrder.RECENTLY_ADDED -> R.string.sort_recently_added
    BookSortOrder.TITLE -> R.string.sort_title
    BookSortOrder.AUTHOR -> R.string.sort_author
    BookSortOrder.PUBLICATION_YEAR -> R.string.sort_publication_year
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    BookFlowTheme {
        SettingsScreen(
            themeMode = AppThemeMode.SYSTEM,
            libraryLayout = LibraryLayout.GRID,
            sortOrder = BookSortOrder.RECENTLY_ADDED,
            appVersion = "1.0.0",
            onThemeChange = {},
            onLibraryLayoutChange = {},
            onSortOrderChange = {},
            onAboutClick = {},
            onLicensesClick = {}
        )
    }
}