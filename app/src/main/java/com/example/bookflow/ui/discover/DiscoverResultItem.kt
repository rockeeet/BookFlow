package com.example.bookflow.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookflow.R
import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.DividerColor
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText

@Composable
fun DiscoverResultItem(
    result: SearchResult,
    isAdded: Boolean,
    onClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(126.dp)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 88.dp, height = 120.dp),
                contentAlignment = Alignment.Center
            ) {
                if (result.coverUrl != null) {
                    AsyncImage(
                        model = result.coverUrl,
                        contentDescription = "${result.title} cover",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = stringResource(R.string.book_cover_placeholder),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = SecondaryText,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
            ) {
                Text(
                    text = result.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = PrimaryText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = result.author,
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    result.firstPublishYear?.let { year ->
                        Text(
                            text = stringResource(
                                R.string.first_published,
                                year
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondaryText
                        )
                    }
                }
            }

            IconButton(
                onClick = onAddClick,
                enabled = !isAdded,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = PrimaryGreen,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isAdded) {
                            Icons.Default.Check
                        } else {
                            Icons.Default.Add
                        },
                        contentDescription = if (isAdded) {
                            "Already added"
                        } else {
                            "Add to library"
                        },
                        tint = Color.White
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 100.dp),
            color = DividerColor,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverResultItemPreview() {
    BookFlowTheme {
        DiscoverResultItem(
            result = SearchResult(
                id = "1",
                title = "The Midnight Library",
                author = "Matt Haig",
                firstPublishYear = 2020,
                coverUrl = null
            ),
            isAdded = true,
            onClick = {},
            onAddClick = {}
        )
    }
}
