package com.example.bookflow.ui.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.ProgressTrackColor
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.StatusCardBackground
import com.example.bookflow.ui.theme.SurfaceSecondary

@Composable
fun ReadingBreakdown(
    totalBooks: Int,
    wantToReadBooks: Int,
    currentlyReadingBooks: Int,
    completedBooks: Int,
    modifier: Modifier = Modifier
) {
    val completedProgress = if (totalBooks > 0) {
        completedBooks.toFloat() / totalBooks
    } else {
        0f
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(R.string.reading_progress),
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryText
            )

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = StatusCardBackground
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.books_completed),
                            style = MaterialTheme.typography.bodyMedium,
                            color = SecondaryText
                        )

                        Text(
                            text = "${(completedProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            color = PrimaryGreen
                        )
                    }

                    LinearProgressIndicator(
                        progress = { completedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(50)),
                        color = PrimaryGreen,
                        trackColor = ProgressTrackColor
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(
                text = stringResource(R.string.reading_breakdown),
                style = MaterialTheme.typography.titleMedium,
                color = PrimaryText
            )

            BreakdownItem(
                title = stringResource(R.string.want_to_read),
                count = wantToReadBooks,
                totalBooks = totalBooks
            )

            BreakdownItem(
                title = stringResource(R.string.currently_reading),
                count = currentlyReadingBooks,
                totalBooks = totalBooks
            )

            BreakdownItem(
                title = stringResource(R.string.completed),
                count = completedBooks,
                totalBooks = totalBooks
            )
        }
    }
}

@Composable
private fun BreakdownItem(
    title: String,
    count: Int,
    totalBooks: Int
) {
    val progress = if (totalBooks > 0) {
        count.toFloat() / totalBooks
    } else {
        0f
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryText
            )

            Text(
                text = count.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = SecondaryText
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(RoundedCornerShape(50)),
            color = PrimaryGreen,
            trackColor = ProgressTrackColor
        )
    }
}