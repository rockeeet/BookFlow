package com.example.bookflow.ui.addbook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookflow.ui.theme.BookFlowTheme

@Composable
fun AddBookFooter(
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonText: String = "Save Book"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onSaveClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                disabledContentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddBookFooterPreview() {
    BookFlowTheme(darkTheme = false) {
        AddBookFooter(
            onSaveClick = {}
        )
    }
}
