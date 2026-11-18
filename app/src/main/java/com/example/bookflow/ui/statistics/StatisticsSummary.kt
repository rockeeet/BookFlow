package com.example.bookflow.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookflow.R

@Composable
fun StatisticsSummary(
    totalBooks: Int,
    completedBooks: Int,
    currentlyReadingBooks: Int,
    pagesRead: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatisticsSummaryCard(
                title = stringResource(R.string.total_books),
                value = totalBooks.toString(),
                modifier = Modifier.weight(1f)
            )

            StatisticsSummaryCard(
                title = stringResource(R.string.books_completed),
                value = completedBooks.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatisticsSummaryCard(
                title = stringResource(R.string.currently_reading),
                value = currentlyReadingBooks.toString(),
                modifier = Modifier.weight(1f)
            )

            StatisticsSummaryCard(
                title = stringResource(R.string.pages_read),
                value = pagesRead.toString(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}