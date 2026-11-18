package com.example.bookflow.ui.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bookflow.data.model.AppThemeMode
import com.example.bookflow.data.model.BookSortOrder
import com.example.bookflow.data.model.LibraryLayout
import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.ui.addbook.AddBookDestination
import com.example.bookflow.ui.addbook.AddBookRoute
import com.example.bookflow.ui.details.BookDetailsDestination
import com.example.bookflow.ui.details.BookDetailsRoute
import com.example.bookflow.ui.discover.DiscoverDestination
import com.example.bookflow.ui.discover.DiscoverRoute
import com.example.bookflow.ui.discover.DiscoverScreen
import com.example.bookflow.ui.editbook.EditBookDestination
import com.example.bookflow.ui.editbook.EditBookRoute
import com.example.bookflow.ui.library.LibraryDestination
import com.example.bookflow.ui.library.LibraryRoute
import com.example.bookflow.ui.library.LibraryScreen
import com.example.bookflow.ui.progress.ReadingProgressDestination
import com.example.bookflow.ui.progress.ReadingProgressRoute
import com.example.bookflow.ui.settings.SettingsDestination
import com.example.bookflow.ui.settings.SettingsScreen
import com.example.bookflow.ui.statistics.StatisticsDestination
import com.example.bookflow.ui.statistics.StatisticsScreen

@Composable
fun BookFlowNavHost(
    navController: NavHostController,
    factory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LibraryDestination.route,
        modifier = modifier
    ) {
        composable(route = LibraryDestination.route) {
            LibraryRoute(
                factory = factory,
                onSearch = {
                    // Поиск подключим позже
                },
                onAddBook = {
                    navController.navigate(AddBookDestination.route)
                },
                onBookClick = { book ->
                    navController.navigate(
                        BookDetailsDestination.createRoute(book.id)
                    )
                }
            )
        }

        composable(route = DiscoverDestination.route) {
            DiscoverRoute(
                factory = factory,
                onResultClick = { result ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("searchResult", result)

                    navController.navigate(BookDetailsDestination.fromDiscoverRoute)
                }
            )
        }

        composable(route = StatisticsDestination.route) {
            StatisticsScreen(
                books = emptyList(),
                onAddBook = {}
            )
        }

        composable(route = SettingsDestination.route) {
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

        composable(route = AddBookDestination.route) {
            AddBookRoute(
                factory = factory,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onBookSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = BookDetailsDestination.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) {
            BookDetailsRoute(
                factory = factory,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { book ->
                    navController.navigate(
                        EditBookDestination.createRoute(book.id)
                    )
                },
                onUpdateProgressClick = { book ->
                    navController.navigate(
                        ReadingProgressDestination.createRoute(book.id)
                    )
                }
            )
        }

        composable(
            route = BookDetailsDestination.fromDiscoverRoute
        ) {
            val searchResult = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<SearchResult>("searchResult")

            BookDetailsRoute(
                factory = factory,
                searchResult = searchResult,
                onBackClick = { navController.popBackStack() },
                onEditClick = { book ->
                    navController.navigate(EditBookDestination.createRoute(book.id))
                },
                onUpdateProgressClick = { book ->
                    navController.navigate(
                        ReadingProgressDestination.createRoute(book.id)
                    )
                }
            )
        }

        composable(
            route = ReadingProgressDestination.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) {
            ReadingProgressRoute(
                factory = factory,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = EditBookDestination.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                }
            )
        ) {
            EditBookRoute(
                factory = factory,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onBookSaved = {
                    navController.popBackStack()
                }
            )
        }
    }
}