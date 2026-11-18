package com.example.bookflow.ui.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.ui.theme.CoverBackground
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.BookFlowTheme

@Composable
fun ReadingProgressBookInfo(
    book: Book,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = book.coverUrl,
            contentDescription = stringResource(R.string.book_cover_placeholder),
            modifier = Modifier
                .width(76.dp)
                .height(108.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(CoverBackground),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                color = PrimaryText
            )

            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadingProgressBookInfoPreview() {
    BookFlowTheme {
        ReadingProgressBookInfo(
            book = Book(
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
            modifier = Modifier.padding(24.dp)
        )
    }
}