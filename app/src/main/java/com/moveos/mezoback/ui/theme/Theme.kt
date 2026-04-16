package com.moveos.mezoback.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkScheme = darkColorScheme(
    primary = CyberBlue,
    secondary = CyberPurple,
    background = Backdrop,
    surface = Panel,
    onPrimary = Backdrop,
    onSecondary = Backdrop,
    onBackground = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color.White,
    onSurfaceVariant = TextSoft,
)

@Composable
fun MEZOBackTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkScheme,
        typography = Typography,
        content = content
    )
}
