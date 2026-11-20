package com.example.bookflow.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.BookFlowTheme

object BookDetailsDestination : NavigationDestination {
    override val route = "book_details/{bookId}"
    override val titleRes = R.string.book_details

    const val fromDiscoverRoute = "book_details_from_discover"

    fun createRoute(bookId: Int): String {
        return "book_details/$bookId"
    }
}

@Composable
fun BookDetailsScreen(
    uiState: BookDetailsUiState,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUpdateProgressClick: () -> Unit,
    onAddToLibraryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val book = uiState.book ?: return

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.book_details),
                canNavigateBack = true,
                actions = {
                    if (uiState.isInLibrary) {
                        IconButton(onClick = { isMenuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.more_options)
                            )
                        }

                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(R.string.edit_book))
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null
                                    )
                                },
                                onClick = {
                                    isMenuExpanded = false
                                    onEditClick()
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(R.string.delete_book),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                },
                                onClick = {
                                    isMenuExpanded = false
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                },
                navigateUp = onBackClick
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = if (uiState.isInLibrary) {
                        onUpdateProgressClick
                    } else {
                        onAddToLibraryClick
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isAddingToLibrary
                ) {
                    Text(
                        text = if (uiState.isInLibrary) {
                            stringResource(R.string.update_reading_progress)
                        } else if (uiState.isAddingToLibrary) {
                            "Adding..."
                        } else {
                            "Add to Library"
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BookCover(
                coverUrl = book.coverUrl
            )

            Spacer(modifier = Modifier.height(16.dp))

            BookInfoSection(
                title = book.title,
                author = book.author,
                year = book.publicationYear?.toString(),
                pages = book.totalPages?.toString()
            )

            Spacer(modifier = Modifier.height(24.dp))

            BookDescription(
                description = book.description.orEmpty()
            )

            Spacer(modifier = Modifier.height(24.dp))

            ReadingStatusCard(
                status = when (book.status) {
                    ReadingStatus.WANT_TO_READ -> "Want to Read"
                    ReadingStatus.CURRENTLY_READING -> "Currently Reading"
                    ReadingStatus.COMPLETED -> "Completed"
                },
                currentPage = book.currentPage,
                totalPages = book.totalPages ?: 0
            )
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                if (!uiState.isDeleting) {
                    showDeleteDialog = false
                }
            },
            shape = RoundedCornerShape(24.dp),
            title = {
                Text(stringResource(R.string.delete_book))
            },
            text = {
                Column {
                    Text(stringResource(R.string.delete_book_confirmation))

                    if (uiState.hasDeleteError) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = stringResource(R.string.delete_book_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onDeleteClick,
                    enabled = !uiState.isDeleting
                ) {
                    if (uiState.isDeleting) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.delete_book),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                    enabled = !uiState.isDeleting
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun BookDetailsRoute(
    factory: ViewModelProvider.Factory,
    onBackClick: () -> Unit,
    onEditClick: (Book) -> Unit,
    searchResult: SearchResult? = null,
    onUpdateProgressClick: (Book) -> Unit
) {
    val viewModel: BookDetailsViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(searchResult) {
        searchResult?.let(viewModel::loadSearchResult)
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onBackClick()
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.hasError -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.unable_to_load_book),
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(stringResource(R.string.back_button))
                }
            }
        }

        uiState.book != null -> {
            BookDetailsScreen(
                uiState = uiState,
                onBackClick = onBackClick,
                onEditClick = {
                    uiState.book?.let(onEditClick)
                },
                onDeleteClick = viewModel::deleteBook,
                onUpdateProgressClick = {
                    uiState.book?.let(onUpdateProgressClick)
                },
                onAddToLibraryClick = viewModel::addToLibrary
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
private fun BookDetailsScreenPreview() {
    BookFlowTheme(darkTheme = false) {
        BookDetailsScreen(
            uiState = BookDetailsUiState(
                book = Book(
                    id = 1,
                    title = "The Creative Act",
                    author = "Rick Rubin",
                    coverUrl = null,
                    publicationYear = 2023,
                    totalPages = 432,
                    description = "A beautiful exploration of the creative process.",
                    status = ReadingStatus.CURRENTLY_READING,
                    currentPage = 181
                )
            ),
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {},
            onUpdateProgressClick = {},
            onAddToLibraryClick = {}
        )
    }
}