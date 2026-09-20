package ir.seometer.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Navy = Color(0xFF071A35)
val Navy2 = Color(0xFF0B2B57)
val Blue = Color(0xFF168BFF)
val Sky = Color(0xFF26B8FF)
val Teal = Color(0xFF18D4C3)
val Green = Color(0xFF22C55E)
val Bg = Color(0xFFF6F9FF)
val SurfaceSoft = Color(0xFFF0F5FC)
val Border = Color(0xFFE6EEF8)
val Text = Color(0xFF10223E)
val Muted = Color(0xFF7C899C)
val Warning = Color(0xFFF59E0B)
val Danger = Color(0xFFEF4444)
val Lavender = Color(0xFFF0E9FF)

private val AppColors = lightColorScheme(
    primary = Blue,
    secondary = Teal,
    tertiary = Green,
    background = Bg,
    surface = Color.White,
    surfaceVariant = SurfaceSoft,
    onPrimary = Color.White,
    onSecondary = Navy,
    onBackground = Text,
    onSurface = Text,
    outline = Border,
    error = Danger
)

private val AppTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 34.sp,
        lineHeight = 43.sp,
        fontWeight = FontWeight.Black,
        color = Text
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 30.sp,
        lineHeight = 39.sp,
        fontWeight = FontWeight.Black,
        color = Text
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 24.sp,
        lineHeight = 33.sp,
        fontWeight = FontWeight.Bold,
        color = Text
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Text
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        lineHeight = 25.sp,
        fontWeight = FontWeight.SemiBold,
        color = Text
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 15.sp,
        lineHeight = 25.sp,
        fontWeight = FontWeight.Normal,
        color = Text
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp,
        lineHeight = 23.sp,
        fontWeight = FontWeight.Normal,
        color = Text
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        color = Muted
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold
    )
)

@Composable
fun SeoMeterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColors,
        typography = AppTypography,
        content = content
    )
}
