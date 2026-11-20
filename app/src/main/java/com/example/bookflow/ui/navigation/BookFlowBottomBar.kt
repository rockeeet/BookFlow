package com.example.bookflow.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookflow.ui.discover.DiscoverDestination
import com.example.bookflow.ui.library.LibraryDestination
import com.example.bookflow.ui.settings.SettingsDestination
import com.example.bookflow.ui.statistics.StatisticsDestination

private data class BottomNavItem(
    val destination: NavigationDestination,
    val icon: ImageVector
)

@Composable
fun BookFlowBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem(LibraryDestination, Icons.AutoMirrored.Outlined.MenuBook),
        BottomNavItem(DiscoverDestination, Icons.Outlined.Explore),
        BottomNavItem(StatisticsDestination, Icons.Outlined.BarChart),
        BottomNavItem(SettingsDestination, Icons.Outlined.Settings)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val label = stringResource(item.destination.titleRes)

            NavigationBarItem(
                selected = currentRoute == item.destination.route,
                onClick = {
                    onNavigate(item.destination.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
