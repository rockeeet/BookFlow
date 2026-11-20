package com.example.bookflow.ui.editbook

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.ui.theme.BookFlowTheme

@Composable
fun EditBookReadingStatus(
    selectedStatus: ReadingStatus,
    onStatusSelected: (ReadingStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.reading_status),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ReadingStatus.entries.forEach { status ->
                val isSelected = status == selectedStatus

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                MaterialTheme.colorScheme.surfaceContainer
                            }
                        )
                        .clickable {
                            onStatusSelected(status)
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 14.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = status.toDisplayName(),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReadingStatus.toDisplayName(): String {
    return when (this) {
        ReadingStatus.WANT_TO_READ ->
            stringResource(R.string.want_to_read)

        ReadingStatus.CURRENTLY_READING ->
            stringResource(R.string.currently_reading)

        ReadingStatus.COMPLETED ->
            stringResource(R.string.completed)
    }
}

@Preview(showBackground = true)
@Composable
private fun EditBookReadingStatusPreview() {
    BookFlowTheme(darkTheme = false) {
        EditBookReadingStatus(
            selectedStatus = ReadingStatus.CURRENTLY_READING,
            onStatusSelected = {},
            modifier = Modifier.padding(24.dp)
        )
    }
}
