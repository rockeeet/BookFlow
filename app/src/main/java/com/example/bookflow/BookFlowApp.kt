package com.example.bookflow

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookflow.data.model.AppThemeMode
import com.example.bookflow.ui.navigation.BookFlowBottomBar
import com.example.bookflow.ui.navigation.BookFlowNavHost
import com.example.bookflow.ui.settings.SettingsViewModel
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.viewmodel.AppViewModelFactory

@Composable
fun BookFlowApp(
    appViewModelFactory: AppViewModelFactory
) {
    val navController = rememberNavController()
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = appViewModelFactory
    )

    val themeMode by settingsViewModel.themeMode.collectAsStateWithLifecycle()
    val systemInDarkTheme = isSystemInDarkTheme()

    val darkTheme = when (themeMode) {
        AppThemeMode.SYSTEM -> systemInDarkTheme
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    BookFlowTheme(darkTheme = darkTheme) {
        Scaffold(
            bottomBar = {
                BookFlowBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            BookFlowNavHost(
                navController = navController,
                factory = appViewModelFactory,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}