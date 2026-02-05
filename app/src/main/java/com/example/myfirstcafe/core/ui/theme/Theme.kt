package com.example.myfirstcafe.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun CafeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = WhiteColor,
            background = BlackColor,
            surface = GrayColor,
            onPrimary = BlackColor,
            onBackground = WhiteColor
        ),
        typography = Typography,
        content = content
    )
}