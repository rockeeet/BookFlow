package com.example.bookflow.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.Background
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.PrimaryGreen

object LibraryDestination : NavigationDestination {
    override val route = "library"
    override val titleRes = R.string.library
}


@Composable
fun LibraryScreen(
    uiState: LibraryUiState,
    onTabSelected: (Int) -> Unit,
    onLayoutChanged: (Boolean) -> Unit,
    onSearch: () -> Unit,
    onAddBook: () -> Unit,
    onRetry: () -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.app_name),
                canNavigateBack = false,
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_button),
                            tint = PrimaryGreen
                        )
                    }
                },
                navigateUp = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBook,
                shape = CircleShape,
                containerColor = PrimaryGreen,
                contentColor = Background
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_book),
                    tint = Background
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ReadingOverview(
                totalBooks = uiState.totalBooks,
                completedBooks = uiState.completedBooks,
                currentlyReadingBooks = uiState.currentlyReadingBooks
            )
            ReadingStatusTabs(
                selectedTabIndex = uiState.selectedTabIndex,
                onTabSelected = onTabSelected
            )
            LibraryControls(
                totalBooks = uiState.totalBooks,
                isGridView = uiState.isGridView,
                onViewModeChange = onLayoutChanged
            )
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.hasError -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.unable_to_load_library),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Button(
                                onClick = onRetry,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }

                else -> {
                    BookCollection(
                        books = uiState.filteredBooks,
                        isGridView = uiState.isGridView,
                        onBookClick = onBookClick,
                        emptyMessageRes = if (uiState.books.isEmpty()) {
                            R.string.empty_library
                        } else {
                            R.string.empty_category
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    val previewBooks = listOf(
        Book(
            id = 1,
            title = "The Creative Act",
            author = "Rick Rubin",
            coverUrl = null,
            publicationYear = 2023,
            totalPages = 432,
            description = null,
            status = ReadingStatus.CURRENTLY_READING,
            currentPage = 181
        )
    )

    BookFlowTheme {
        LibraryScreen(
            uiState = LibraryUiState(
                books = previewBooks,
                isLoading = false
            ),
            onTabSelected = {},
            onLayoutChanged = {},
            onSearch = {},
            onAddBook = {},
            onRetry = {},
            onBookClick = {}
        )
    }
}


