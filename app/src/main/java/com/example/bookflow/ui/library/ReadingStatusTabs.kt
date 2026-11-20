package com.example.bookflow.ui.library

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.R
import com.example.bookflow.ui.theme.BookFlowTheme


@Composable
fun ReadingStatusTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SecondaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    selectedTabIndex,
                    matchContentSize = true
                ),
                color = MaterialTheme.colorScheme.primary
            )
        },
        divider = {}
    ) {
        val tabs = listOf(
            R.string.all,
            R.string.want_to_read,
            R.string.currently_reading,
            R.string.completed
        )

        tabs.forEachIndexed { index, titleRes ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = {
                    Text(
                        text = stringResource(titleRes),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReadingStatusTabsPreview() {
    BookFlowTheme(darkTheme = false) {
        ReadingStatusTabs(0, {})
    }
}