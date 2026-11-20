package com.example.bookflow.ui.statistics

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.BookFlowTheme

object StatisticsDestination : NavigationDestination {
    override val route = "statistics"
    override val titleRes = R.string.statistics
}
@Composable
fun StatisticsScreen(
    uiState: StatisticsUiState,
    onAddBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.reading_statistics),
                canNavigateBack = false,
                actions = {},
                navigateUp = {}
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.hasError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Couldn't load statistics. Please try again later."
                    )
                }
            }

            uiState.totalBooks == 0 -> {
                StatisticsEmptyState(
                    onAddBook = onAddBook
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    StatisticsSummary(
                        totalBooks = uiState.totalBooks,
                        completedBooks = uiState.completedBooks,
                        currentlyReadingBooks = uiState.currentlyReadingBooks,
                        pagesRead = uiState.pagesRead
                    )

                    ReadingBreakdown(
                        wantToReadBooks = uiState.wantToReadBooks,
                        currentlyReadingBooks = uiState.currentlyReadingBooks,
                        completedBooks = uiState.completedBooks,

                        completedProgress = uiState.completedProgress,
                        completedProgressPercent = uiState.completedProgressPercent,

                        wantToReadProgress = uiState.wantToReadProgress,
                        wantToReadProgressPercent = uiState.wantToReadProgressPercent,

                        currentlyReadingProgress = uiState.currentlyReadingProgress,
                        currentlyReadingProgressPercent = uiState.currentlyReadingProgressPercent
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun StatisticsEmptyState(
    onAddBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.statistics_empty_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.statistics_empty_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAddBook,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = stringResource(R.string.add_first_book),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatisticsScreenPreview() {
    BookFlowTheme(darkTheme = false) {
        StatisticsScreen(
            uiState = StatisticsUiState(
                totalBooks = 3,
                completedBooks = 1,
                currentlyReadingBooks = 1,
                wantToReadBooks = 1,
                pagesRead = 440,
                completedProgress = 1f / 3f,
                currentlyReadingProgress = 1f / 3f,
                wantToReadProgress = 1f / 3f,
                isLoading = false,
                hasError = false
            ),
            onAddBook = {}
        )
    }
}