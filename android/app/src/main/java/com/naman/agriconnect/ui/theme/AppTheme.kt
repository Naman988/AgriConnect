package com.naman.agriconnect.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define the color palette for your app
private val DarkGreen = Color(0xFF386641)
private val LightGreen = Color(0xFF6A994E)
private val Beige = Color(0xFFF2E8CF)
private val Brown = Color(0xFFA77B5A)
private val White = Color(0xFFFFFFFF)
private val Black = Color(0xFF000000)


private val LightColorScheme = lightColorScheme(
    primary = DarkGreen,
    secondary = LightGreen,
    tertiary = Brown,
    background = Beige,
    surface = White,
    onPrimary = White,
    onSecondary = Black,
    onTertiary = White,
    onBackground = Black,
    onSurface = Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    secondary = DarkGreen,
    tertiary = Brown,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF2C2B2F),
    onPrimary = Black,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White,
)

@Composable
fun FoodLensTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assumes Typography.kt exists
        content = content
    )
}
