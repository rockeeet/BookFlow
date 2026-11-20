package com.example.bookflow.ui.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.data.model.SearchType
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination

object DiscoverDestination : NavigationDestination {
    override val route = "discover"
    override val titleRes = R.string.discover
}
@Composable
fun DiscoverScreen(
    uiState: DiscoverUiState,
    addedBookIds: Set<String>,
    onSearch: (query: String, type: SearchType) -> Unit,
    onResultClick: (SearchResult) -> Unit,
    onAddBook: (SearchResult) -> Unit,
    onMessageShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember {
        mutableStateOf("")
    }

    var selectedType by remember {
        mutableStateOf(SearchType.TITLE)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            onMessageShown()
        }
    }

    Scaffold(
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.discover),
                canNavigateBack = false,
                actions = {},
                navigateUp = {}
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DiscoverSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    onSearch(query, selectedType)
                }
            )

            DiscoverSearchOptions(
                selectedType = selectedType,
                onTypeSelected = { newType ->
                    selectedType = newType

                    if (query.isNotBlank()) {
                        onSearch(query, newType)
                    }
                }
            )

            if (uiState.hasSearched) {
                DiscoverResultsHeader()
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (!uiState.hasSearched) {
                DiscoverEmptyState(
                    modifier = Modifier.weight(1f)
                )
            } else if (!uiState.isLoading && uiState.error == null && uiState.results.isEmpty()) {
                DiscoverNoResultsState(
                    selectedType = selectedType,
                    modifier = Modifier.weight(1f)
                )
            } else {
                DiscoverResults(
                    results = uiState.results,
                    addedBookIds = addedBookIds,
                    onResultClick = onResultClick,
                    onAddBook = onAddBook,
                    modifier = Modifier.weight(1f)
                )
            }

            OpenLibraryAttribution(
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 12.dp
                )
            )
        }
    }
}

@Composable
fun OpenLibraryAttribution(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.open_library_attribution),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}