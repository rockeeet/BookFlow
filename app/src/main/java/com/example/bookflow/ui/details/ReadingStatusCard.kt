package com.example.bookflow.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookflow.R
import com.example.bookflow.ui.theme.PrimaryGreen
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText
import com.example.bookflow.ui.theme.StatusCardBackground

@Composable
fun ReadingStatusCard(
    status: String,
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (totalPages > 0) {
        (currentPage.toFloat() / totalPages).coerceIn(0f, 1f)
    } else {
        0f
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(StatusCardBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(R.string.reading_status),
                    fontSize = 11.sp,
                    color = SecondaryText
                )

                Text(
                    text = status,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryText
                )
            }

            Text(
                text = "›",
                fontSize = 24.sp,
                color = SecondaryText
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Page $currentPage of $totalPages",
                fontSize = 11.sp,
                color = SecondaryText
            )

            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(50)),
            color = PrimaryGreen,
            trackColor = Color(0xFFD5DCCF)
        )
    }
}

@Preview(showBackground = true, widthDp = 364)
@Composable
private fun ReadingStatusCardPreview() {
    ReadingStatusCard(
        status = "Currently Reading",
        currentPage = 181,
        totalPages = 432
    )
}