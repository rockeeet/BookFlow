package com.example.bookflow.ui.progress

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText

object ReadingProgressDestination : NavigationDestination {
    override val route = "reading_progress/{bookId}"
    override val titleRes = R.string.reading_progress

    fun createRoute(bookId: Int): String {
        return "reading_progress/$bookId"
    }
}
@Composable
fun ReadingProgressScreen(
    uiState: ReadingProgressUiState,
    onNavigateBack: () -> Unit,
    onCurrentPageChange: (String) -> Unit,
    onIncrementPage: () -> Unit,
    onDecrementPage: () -> Unit,
    onSaveProgress: () -> Unit,
    onMarkCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val book = uiState.book

    if (uiState.hasError || book == null) {
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
                onClick = onNavigateBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen
                )
            ) {
                Text(stringResource(R.string.back_button))
            }
        }
        return
    }
    val displayedPage = uiState.displayedPage
    val enteredPage = uiState.enteredPage
    val maxPages = uiState.maxPages

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Background,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.reading_progress),
                canNavigateBack = true,
                actions = {},
                navigateUp = onNavigateBack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onSaveProgress,
                    enabled = uiState.canSave && !uiState.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryGreen,
                        contentColor = Background
                    )
                ) {
                    Text(
                        text = stringResource(R.string.save_progress),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                OutlinedButton(
                    onClick = onMarkCompleted,
                    enabled = !uiState.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryText
                    )
                ) {
                    Text(
                        text = stringResource(R.string.mark_as_completed),
                        style = MaterialTheme.typography.labelLarge
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
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            ReadingProgressBookInfo(book = book)

            ProgressOverview(
                currentPage = displayedPage,
                totalPages = book.totalPages
            )

            UpdateProgress(
                currentPage = uiState.currentPageInput,
                onCurrentPageChange = onCurrentPageChange,
                onIncrementPage = onIncrementPage,
                onDecrementPage = onDecrementPage,
                enabled = !uiState.isSaving
            )
            if (uiState.hasSaveError) {
                Text(
                    text = stringResource(R.string.progress_save_error),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (maxPages != null && enteredPage != null && enteredPage > maxPages) {
                Text(
                    text = stringResource(R.string.progress_cannot_exceed_total),
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryText
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingProgressScreenPreview() {
    ReadingProgressScreen(
        uiState = ReadingProgressUiState(
            book = Book(
                id = 1,
                title = "The Silent Patient",
                author = "Alex Michaelides",
                coverUrl = null,
                publicationYear = 2019,
                totalPages = 336,
                description = "A psychological thriller",
                status = ReadingStatus.CURRENTLY_READING,
                currentPage = 120
            ),
            currentPageInput = "120"
        ),
        onNavigateBack = {},
        onCurrentPageChange = {},
        onIncrementPage = {},
        onDecrementPage = {},
        onSaveProgress = {},
        onMarkCompleted = {}
    )
}