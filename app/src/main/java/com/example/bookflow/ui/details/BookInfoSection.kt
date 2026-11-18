package com.example.bookflow.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookflow.ui.theme.DividerColor
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText

@Composable
fun BookInfoSection(
    title: String,
    author: String,
    year: String?,
    pages: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Serif,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = author,
            fontSize = 13.sp,
            color = SecondaryText
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookMetadataItem(
                value = year ?: "—",
                label = "Published",
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(width = 1.dp, height = 36.dp)
                    .background(DividerColor)
            )

            BookMetadataItem(
                value = pages ?: "—",
                label = "Pages",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BookMetadataItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            fontFamily = FontFamily.Serif,
            fontSize = 19.sp,
            color = PrimaryText
        )

        Text(
            text = label,
            fontSize = 10.sp,
            color = SecondaryText
        )
    }
}

@Preview(showBackground = true, widthDp = 364)
@Composable
private fun BookInfoSectionPreview() {
    BookInfoSection(
        title = "The Creative Act",
        author = "Rick Rubin",
        year = "2023",
        pages = "432"
    )
}