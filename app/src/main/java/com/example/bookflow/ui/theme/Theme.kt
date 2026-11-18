package com.example.bookflow.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


private val BookFlowColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Background,

    secondary = StatusCardBackground,
    onSecondary = PrimaryText,

    tertiary = CoverPlaceholderColor,
    onTertiary = PrimaryText,

    background = Background,
    onBackground = PrimaryText,

    surface = Background,
    onSurface = PrimaryText,

    surfaceVariant = SurfaceSecondary,
    onSurfaceVariant = SecondaryText,

    outline = DividerColor
)


@Composable
fun BookFlowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BookFlowColorScheme,
        typography = Typography,
        content = content
    )
}