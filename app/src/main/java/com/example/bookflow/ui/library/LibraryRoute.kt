package com.example.bookflow.ui.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookflow.data.model.Book

@Composable
fun LibraryRoute(
    factory: ViewModelProvider.Factory,
    onSearch: () -> Unit,
    onAddBook: () -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: LibraryViewModel = viewModel(
        factory = factory
    )

    val uiState by viewModel.uiState.collectAsState()

    LibraryScreen(
        uiState = uiState,
        onTabSelected = viewModel::onTabSelected,
        onLayoutChanged = viewModel::onLayoutChanged,
        onSearch = onSearch,
        onAddBook = onAddBook,
        onRetry = viewModel::retryLoading,
        onBookClick = onBookClick,
        modifier = modifier
    )
}