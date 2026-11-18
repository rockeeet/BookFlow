package com.example.bookflow.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.ui.theme.Background
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.CoverBackground
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.ProgressTrackColor
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.StatusCardBackground
import kotlin.math.roundToInt


@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Background
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(CoverBackground),
                contentAlignment = Alignment.Center
            ) {
                if (book.coverUrl != null) {
                    AsyncImage(
                        model = book.coverUrl,
                        contentDescription = "Book cover",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Text(
                        text = stringResource(R.string.book_cover_placeholder),
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryText
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryText,
                    maxLines = 2
                )

                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryText,
                    maxLines = 1
                )

                Text(
                    text = book.status.toDisplayName(),
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryText,
                    modifier = Modifier
                        .background(
                            color = StatusCardBackground,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                if (book.status == ReadingStatus.CURRENTLY_READING) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = { book.progress },
                            modifier = Modifier.fillMaxWidth(),
                            color = PrimaryGreen,
                            trackColor = ProgressTrackColor
                        )

                        Text(
                            text = "${(book.progress * 100).roundToInt()}% read",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryText
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

private fun ReadingStatus.toDisplayName(): String {
    return when (this) {
        ReadingStatus.WANT_TO_READ -> "Want to Read"
        ReadingStatus.CURRENTLY_READING -> "Currently Reading"
        ReadingStatus.COMPLETED -> "Completed"
    }
}


@Preview(showBackground = true)
@Composable
fun BookCardPreview() {
    BookFlowTheme {
        BookCard(
            book = Book(
                id = 1,
                title = "The Creative Act",
                author = "Rick Rubin",
                coverUrl = null,
                status = ReadingStatus.CURRENTLY_READING,
                publicationYear = 2020,
                totalPages = 367,
                currentPage = 45,
                description = null
            ),
            onClick = {},
            modifier = Modifier.width(200.dp)
        )
    }
}