package com.example.userfeed.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyle {
    val displayLarge: TextStyle @Composable get() = MaterialTheme.typography.displayLarge
    val displayMedium: TextStyle @Composable get() = MaterialTheme.typography.displayMedium
    val displaySmall: TextStyle @Composable get() = MaterialTheme.typography.displaySmall
    val headlineLarge: TextStyle @Composable get() = MaterialTheme.typography.headlineLarge
    val headlineMedium: TextStyle @Composable get() = MaterialTheme.typography.headlineMedium
    val headlineSmall: TextStyle @Composable get() = MaterialTheme.typography.headlineSmall
    val titleLarge: TextStyle @Composable get() = MaterialTheme.typography.titleLarge
    val titleMedium: TextStyle @Composable get() = MaterialTheme.typography.titleMedium
    val titleSmall: TextStyle @Composable get() = MaterialTheme.typography.titleSmall
    val bodyLarge: TextStyle @Composable get() = MaterialTheme.typography.bodyLarge
    val bodyMedium: TextStyle @Composable get() = MaterialTheme.typography.bodyMedium
    val bodySmall: TextStyle @Composable get() = MaterialTheme.typography.bodySmall
    val labelLarge: TextStyle @Composable get() = MaterialTheme.typography.labelLarge
    val labelMedium: TextStyle @Composable get() = MaterialTheme.typography.labelMedium
    val labelSmall: TextStyle @Composable get() = MaterialTheme.typography.labelSmall
}

object AppColor {
    val primary @Composable get() = MaterialTheme.colorScheme.primary
    val onPrimary @Composable get() = MaterialTheme.colorScheme.onPrimary
    val primaryContainer @Composable get() = MaterialTheme.colorScheme.primaryContainer
    val onPrimaryContainer @Composable get() = MaterialTheme.colorScheme.onPrimaryContainer
    val secondary @Composable get() = MaterialTheme.colorScheme.secondary
    val onSecondary @Composable get() = MaterialTheme.colorScheme.onSecondary
    val error @Composable get() = MaterialTheme.colorScheme.error
    val onError @Composable get() = MaterialTheme.colorScheme.onError
    val background @Composable get() = MaterialTheme.colorScheme.background
    val onBackground @Composable get() = MaterialTheme.colorScheme.onBackground
    val surface @Composable get() = MaterialTheme.colorScheme.surface
    val onSurface @Composable get() = MaterialTheme.colorScheme.onSurface
    val surfaceVariant @Composable get() = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceVariant @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
}

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
