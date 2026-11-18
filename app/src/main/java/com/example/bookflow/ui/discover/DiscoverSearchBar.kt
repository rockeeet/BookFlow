
package com.example.bookflow.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.ui.theme.Background
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.SurfaceSecondary

@Composable
fun DiscoverSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = PrimaryText
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryText
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceSecondary,
                focusedContainerColor = SurfaceSecondary,
                disabledContainerColor = SurfaceSecondary,
                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                cursorColor = PrimaryGreen,
                focusedTextColor = PrimaryText,
                unfocusedTextColor = PrimaryText
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = SecondaryText
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.clear_search),
                            tint = SecondaryText
                        )
                    }
                }
            }
        )

        IconButton(
            onClick = onSearch,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(PrimaryGreen)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_button),
                tint = Background
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverSearchBarPreview() {
    BookFlowTheme {
        DiscoverSearchBar(
            query = "Matt Haig",
            onQueryChange = {},
            onSearch = {}
        )
    }
}
