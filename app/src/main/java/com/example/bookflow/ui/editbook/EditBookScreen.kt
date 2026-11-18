package com.example.bookflow.ui.editbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.ui.addbook.AddBookCoverUpload
import com.example.bookflow.ui.addbook.AddBookFooter
import com.example.bookflow.ui.addbook.AddBookTextField
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.BookFlowTheme
import java.util.Calendar

object EditBookDestination : NavigationDestination {
    override val route = "edit_book/{bookId}"
    override val titleRes = R.string.edit_book

    fun createRoute(bookId: Int): String {
        return "edit_book/$bookId"
    }
}

@Composable
fun EditBookScreen(
    uiState: EditBookUiState,
    onNavigateBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTotalPagesChange: (String) -> Unit,
    onPublicationYearChange: (String) -> Unit,
    onStatusChange: (ReadingStatus) -> Unit,
    onPickCover: () -> Unit,
    onSaveChanges: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentYear = Calendar.getInstance()
        .get(Calendar.YEAR)

    Scaffold(
        modifier = modifier,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.edit_book),
                canNavigateBack = true,
                actions = {},
                navigateUp = onNavigateBack
            )
        },
        bottomBar = {
            AddBookFooter(
                onSaveClick = onSaveChanges,
                enabled = !uiState.isSaving &&
                        !uiState.isLoading &&
                        !uiState.hasLoadError,
                buttonText = stringResource(R.string.save_changes)
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.hasLoadError -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.unable_to_load_book),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Button(
                        onClick = onNavigateBack
                    ) {
                        Text(stringResource(R.string.back_button))
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                        .padding(top = 12.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AddBookCoverUpload(
                        coverUri = uiState.coverUri,
                        onAddCoverClick = onPickCover
                    )

                    AddBookTextField(
                        label = stringResource(R.string.book_title),
                        value = uiState.title,
                        onValueChange = onTitleChange,
                        placeholder = stringResource(R.string.enter_book_title),
                        isError = uiState.titleError,
                        errorMessage = if (uiState.titleError) {
                            stringResource(R.string.title_required_error)
                        } else null
                    )

                    AddBookTextField(
                        label = stringResource(R.string.author),
                        value = uiState.author,
                        onValueChange = onAuthorChange,
                        placeholder = stringResource(R.string.enter_author),
                        isError = uiState.authorError,
                        errorMessage = if (uiState.authorError) {
                            stringResource(R.string.author_required_error)
                        } else null
                    )

                    AddBookTextField(
                        label = stringResource(R.string.description),
                        value = uiState.description,
                        onValueChange = onDescriptionChange,
                        placeholder = stringResource(R.string.enter_description),
                        isOptional = true,
                        singleLine = false,
                        minLines = 3,
                        maxLines = 3
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AddBookTextField(
                            label = stringResource(R.string.total_pages),
                            value = uiState.totalPages,
                            onValueChange = onTotalPagesChange,
                            modifier = Modifier.weight(1f),
                            placeholder = stringResource(R.string.pages_placeholder),
                            isOptional = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            isError = uiState.pagesError,
                            errorMessage = if (uiState.pagesError) {
                                stringResource(R.string.invalid_pages_error)
                            } else null
                        )

                        AddBookTextField(
                            label = stringResource(R.string.publication_year),
                            value = uiState.publicationYear,
                            onValueChange = onPublicationYearChange,
                            modifier = Modifier.weight(1f),
                            placeholder = stringResource(R.string.year_placeholder),
                            isOptional = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            isError = uiState.yearError,
                            errorMessage = if (uiState.yearError) {
                                stringResource(
                                    R.string.invalid_year_error,
                                    currentYear
                                )
                            } else null
                        )
                    }

                    EditBookReadingStatus(
                        selectedStatus = uiState.status,
                        onStatusSelected = onStatusChange
                    )
                }
            }
        }
    }
}
