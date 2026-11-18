
package com.example.bookflow.ui.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.data.model.SearchType
import com.example.bookflow.ui.theme.Background
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.SurfaceSecondary

@Composable
fun DiscoverSearchOptions(
    selectedType: SearchType,
    onTypeSelected: (SearchType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = stringResource(R.string.search_by),
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryText
        )

        DiscoverSearchTypeSelector(
            selectedType = selectedType,
            onTypeSelected = onTypeSelected,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DiscoverSearchTypeSelector(
    selectedType: SearchType,
    onTypeSelected: (SearchType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceSecondary)
            .padding(4.dp)
    ) {
        SearchType.entries.forEach { type ->
            val isSelected = selectedType == type

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (isSelected) Background else Background.copy(alpha = 0f)
                    )
                    .clickable { onTypeSelected(type) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (type) {
                        SearchType.TITLE -> "Title"
                        SearchType.AUTHOR -> "Author"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) PrimaryGreen else SecondaryText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverSearchOptionsPreview() {
    BookFlowTheme {
        DiscoverSearchOptions(
            selectedType = SearchType.TITLE,
            onTypeSelected = {}
        )
    }
}
