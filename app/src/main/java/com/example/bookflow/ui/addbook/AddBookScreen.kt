package com.example.bookflow.ui.addbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.BookFlowTheme
import java.util.Calendar

object AddBookDestination : NavigationDestination {
    override val route = "add_book"
    override val titleRes = R.string.add_new_book
}

@Composable
fun AddBookScreen(
    uiState: AddBookUiState,
    onTitleChanged: (String) -> Unit,
    onAuthorChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onTotalPagesChanged: (String) -> Unit,
    onPublicationYearChanged: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onPickCover: () -> Unit,
    onSaveBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentYear = Calendar.getInstance()
        .get(Calendar.YEAR)

    Scaffold(
        modifier = modifier,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.add_new_book),
                canNavigateBack = true,
                actions = {},
                navigateUp = onNavigateBack
            )
        },
        bottomBar = {
            AddBookFooter(
                onSaveClick = onSaveBook,
                enabled = !uiState.isSaving
            )
        }
    ) { innerPadding ->
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

            if (uiState.hasSaveError) {
                Text(
                    text = stringResource(R.string.book_save_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            AddBookTextField(
                label = stringResource(R.string.book_title),
                value = uiState.title,
                onValueChange = onTitleChanged,
                placeholder = stringResource(R.string.enter_book_title),
                isError = uiState.titleError,
                errorMessage = if (uiState.titleError) {
                    stringResource(R.string.title_required_error)
                } else null
            )

            AddBookTextField(
                label = stringResource(R.string.author),
                value = uiState.author,
                onValueChange = onAuthorChanged,
                placeholder = stringResource(R.string.enter_author),
                isError = uiState.authorError,
                errorMessage = if (uiState.authorError) {
                    stringResource(R.string.author_required_error)
                } else null
            )

            AddBookTextField(
                label = stringResource(R.string.description),
                value = uiState.description,
                onValueChange = onDescriptionChanged,
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
                    onValueChange = onTotalPagesChanged,
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
                    onValueChange = onPublicationYearChanged,
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

            AddBookDefaultStatus()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddBookScreenPreview() {
    BookFlowTheme {
        AddBookScreen(
            uiState = AddBookUiState(),
            onTitleChanged = {},
            onAuthorChanged = {},
            onDescriptionChanged = {},
            onTotalPagesChanged = {},
            onPublicationYearChanged = {},
            onNavigateBack = {},
            onPickCover = {},
            onSaveBook = {}
        )
    }
}
