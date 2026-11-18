package com.example.bookflow.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.Book
import com.example.bookflow.ui.theme.SecondaryText


@Composable
fun BookCollection(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    isGridView: Boolean,
    emptyMessageRes: Int,
    modifier: Modifier = Modifier
) {
    if (books.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(emptyMessageRes),
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryText
            )
        }
    } else if (isGridView) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = books,
                key = { it.id }
            ) { book ->
                BookCard(
                    book = book,
                    onClick = {
                        onBookClick(book)
                    }
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = books,
                key = { it.id }
            ) { book ->
                BookCard(
                    book = book,
                    onClick = {
                        onBookClick(book)
                    }
                )
            }
        }
    }
}
