package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = TerracottaLight,
    onPrimary = NightCanvas,
    primaryContainer = TerracottaDark,
    onPrimaryContainer = SandCanvas,
    secondary = SageGreenLight,
    onSecondary = NightCanvas,
    secondaryContainer = SageGreen,
    onSecondaryContainer = SandCanvas,
    tertiary = SunGoldLight,
    onTertiary = NightCanvas,
    background = NightCanvas,
    onBackground = NightTextPrimary,
    surface = NightSurface,
    onSurface = NightTextPrimary,
    surfaceVariant = NightSurfaceVariant,
    onSurfaceVariant = NightTextSecondary,
    outline = NightBorder,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = Terracotta,
    onPrimary = SandSurface,
    primaryContainer = TerracottaLight,
    onPrimaryContainer = SandTextPrimary,
    secondary = SageGreen,
    onSecondary = SandSurface,
    secondaryContainer = SageGreenContainer,
    onSecondaryContainer = SandTextPrimary,
    tertiary = SunGold,
    onTertiary = SandSurface,
    background = SandCanvas,
    onBackground = SandTextPrimary,
    surface = SandSurface,
    onSurface = SandTextPrimary,
    surfaceVariant = SandSurfaceVariant,
    onSurfaceVariant = SandTextSecondary,
    outline = SandBorder,
  )

@Composable
fun AuraTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

// Backward-compatible alias for existing tests
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  AuraTheme(darkTheme = darkTheme, content = content)
}

