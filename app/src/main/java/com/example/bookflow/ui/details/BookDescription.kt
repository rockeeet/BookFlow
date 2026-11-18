package com.example.bookflow.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookflow.ui.theme.PrimaryText
import com.example.bookflow.ui.theme.SecondaryText


@Composable
fun BookDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "About",
            fontFamily = FontFamily.Serif,
            fontSize = 19.sp,
            color = PrimaryText
        )

        Text(
            text = description,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = SecondaryText
        )
    }
}

@Preview(showBackground = true, widthDp = 364)
@Composable
private fun BookDescriptionPreview() {
    BookDescription(
        description = "A beautiful exploration of the creative process, " +
                "offering insights into how to approach creativity and " +
                "bring new ideas into the world."
    )
}