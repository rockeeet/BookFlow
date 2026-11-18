
package com.example.bookflow.ui.addbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookflow.R
import com.example.bookflow.ui.theme.BookFlowTheme
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.StatusCardBackground

@Composable
fun AddBookDefaultStatus(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(13.dp),
        color = StatusCardBackground
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                tint = PrimaryGreen,
                modifier = Modifier.size(21.dp)
            )

            Column {
                Text(
                    text = stringResource(R.string.default_reading_status),
                    style = MaterialTheme.typography.labelMedium,
                    color = PrimaryText
                )

                Text(
                    text = stringResource(R.string.default_reading_status_hint),
                    style = MaterialTheme.typography.labelSmall,
                    color = SecondaryText
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddBookDefaultStatusPreview() {
    BookFlowTheme {
        AddBookDefaultStatus(
            modifier = Modifier.padding(24.dp)
        )
    }
}
