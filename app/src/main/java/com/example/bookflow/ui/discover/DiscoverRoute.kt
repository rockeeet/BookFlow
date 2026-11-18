package com.example.bookflow.ui.discover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookflow.data.model.SearchResult

@Composable
fun DiscoverRoute(
    factory: ViewModelProvider.Factory,
    onResultClick: (SearchResult) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: DiscoverViewModel = viewModel(
        factory = factory
    )

    val uiState by viewModel.uiState.collectAsState()

    DiscoverScreen(
        uiState = uiState,
        addedBookIds = uiState.addedBookIds,
        onSearch = viewModel::searchBooks,
        onResultClick = onResultClick,
        onAddBook = viewModel::addBookToLibrary,
        onMessageShown = viewModel::clearMessage,
        modifier = modifier
    )
}