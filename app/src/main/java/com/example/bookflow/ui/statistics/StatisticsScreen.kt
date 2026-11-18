package com.example.bookflow.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.ui.components.BookFlowTopAppBar
import com.example.bookflow.ui.navigation.NavigationDestination
import com.example.bookflow.ui.theme.Background
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.BookFlowTheme

object StatisticsDestination : NavigationDestination {
    override val route = "statistics"
    override val titleRes = R.string.statistics
}
@Composable
fun StatisticsScreen(
    books: List<Book>,
    onAddBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalBooks = books.size

    val completedBooks = books.count {
        it.status == ReadingStatus.COMPLETED
    }

    val currentlyReadingBooks = books.count {
        it.status == ReadingStatus.CURRENTLY_READING
    }

    val wantToReadBooks = books.count {
        it.status == ReadingStatus.WANT_TO_READ
    }

    val pagesRead = books.sumOf {
        it.currentPage.coerceAtLeast(0)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Background,
        topBar = {
            BookFlowTopAppBar(
                title = stringResource(R.string.reading_statistics),
                canNavigateBack = false,
                actions = {},
                navigateUp = {}
            )
        }
    ) { innerPadding ->
        if (books.isEmpty()) {
            StatisticsEmptyState(
                onAddBook = onAddBook,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                StatisticsSummary(
                    totalBooks = totalBooks,
                    completedBooks = completedBooks,
                    currentlyReadingBooks = currentlyReadingBooks,
                    pagesRead = pagesRead
                )

                ReadingBreakdown(
                    totalBooks = totalBooks,
                    wantToReadBooks = wantToReadBooks,
                    currentlyReadingBooks = currentlyReadingBooks,
                    completedBooks = completedBooks
                )

                Spacer(modifier = Modifier.height(8.dp))
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
            color = PrimaryText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.statistics_empty_description),
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryText,
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
                containerColor = PrimaryGreen,
                contentColor = Background
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
    BookFlowTheme {
        StatisticsScreen(
            books = listOf(
                Book(
                    id = 1,
                    title = "The Midnight Library",
                    author = "Matt Haig",
                    coverUrl = null,
                    publicationYear = 2020,
                    totalPages = 304,
                    description = null,
                    status = ReadingStatus.CURRENTLY_READING,
                    currentPage = 120
                ),
                Book(
                    id = 2,
                    title = "Atomic Habits",
                    author = "James Clear",
                    coverUrl = null,
                    publicationYear = 2018,
                    totalPages = 320,
                    description = null,
                    status = ReadingStatus.COMPLETED,
                    currentPage = 320
                ),
                Book(
                    id = 3,
                    title = "Dune",
                    author = "Frank Herbert",
                    coverUrl = null,
                    publicationYear = 1965,
                    totalPages = 500,
                    description = null,
                    status = ReadingStatus.WANT_TO_READ,
                    currentPage = 0
                )
            ),
            onAddBook = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatisticsEmptyStatePreview() {
    BookFlowTheme {
        StatisticsScreen(
            books = emptyList(),
            onAddBook = {}
        )
    }
}