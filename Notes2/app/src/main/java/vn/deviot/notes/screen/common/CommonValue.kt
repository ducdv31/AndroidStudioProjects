package vn.deviot.notes.screen.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class TextStyleValue(
    val textTitleAlertDialog: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )
)

val LocalTextStyleNotes = compositionLocalOf { TextStyleValue() }

val MaterialTheme.textStyleValue: TextStyleValue
    @Composable
    @ReadOnlyComposable
    get() = LocalTextStyleNotes.current