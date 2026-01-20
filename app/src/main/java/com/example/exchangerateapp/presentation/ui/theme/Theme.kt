package com.example.exchangerateapp.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF66BB6A),
    onPrimary = Color.Black,

    secondary = Color(0xFF81C784),
    onSecondary = Color.Black,

    background = Color(0xFF121212),
    onBackground = Color(0xFFEDEDED),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFEDEDED),

    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Color(0xFFBDBDBD),

    error = Color(0xFFEF5350),
    onError = Color.Black
)


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,

    secondary = Color(0xFF66BB6A),
    onSecondary = Color.Black,

    background = Color(0xFFF8F9F8),
    onBackground = Color(0xFF1C1C1C),

    surface = Color.White,
    onSurface = Color(0xFF1C1C1C),

    surfaceVariant = Color(0xFFE6ECE7),
    onSurfaceVariant = Color(0xFF3A3A3A),

    error = Color(0xFFD32F2F),
    onError = Color.White
)


@Composable
fun ExchangeRateAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}