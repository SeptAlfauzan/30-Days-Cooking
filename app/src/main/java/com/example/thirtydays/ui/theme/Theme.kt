package com.example.thirtydays.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = darkblue03,
    primaryVariant = darkblue03,
    secondary = Teal200,

    background = darkblue01,
    surface = darkblue02,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = orange01,
    onSurface = orange01,
)

private val LightColorPalette = lightColors(
    primary = teal02,
    primaryVariant = Purple700,
    secondary = Teal200,

    background = teal01,
    surface = teal02,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun ThirtyDaysTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}