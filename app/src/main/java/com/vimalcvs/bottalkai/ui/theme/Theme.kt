package com.vimalcvs.bottalkai.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green,
    primaryVariant = GreenShadow,
    secondary = StrokeColorDark,
    secondaryVariant = CardBackgroundDark,
    background = BackgroundDark,
    surface = TextColorDark,
    error = Red,
    onPrimary = CardBorderDark,
    onSecondary = CapabilitiesBackgroundDark,
    onSurface = InactiveInputDark,
    onBackground = MessageBackgroundDark
)

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = GreenShadow,
    secondary = StrokeColor,
    secondaryVariant = CardBackground,
    background = Background,
    surface = TextColor,
    error = Red,
    onPrimary = CardBorder,
    onSecondary = CapabilitiesBackground,
    onSurface = InactiveInput,
    onBackground = MessageBackground
)

@Composable
fun BotTalkAITheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = if (darkTheme) {
        TypographyDark
    } else {
        Typography
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}