package com.example.bookflow.ui.progress

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

@Composable
fun ProgressOverview(
    currentPage: Int,
    totalPages: Int?,
    modifier: Modifier = Modifier
) {
    val hasTotalPages = totalPages != null && totalPages > 0
    val progress = if (hasTotalPages) {
        (currentPage.toFloat() / totalPages!!).coerceIn(0f, 1f)
    } else {
        0f
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(StatusCardBackground)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.progress_overview),
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryText
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = currentPage.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = PrimaryText
                )
                Text(
                    text = stringResource(R.string.pages_read),
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryText
                )
            }

            if (hasTotalPages) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "$totalPages",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PrimaryText
                    )
                    Text(
                        text = stringResource(R.string.total_pages),
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryText
                    )
                }

                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryGreen
                )
            }
        }

        if (hasTotalPages) {
            LinearProgressIndicator(
                progress = { progress },
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