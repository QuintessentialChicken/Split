package com.example.split.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SubtitleText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    text: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign,
        text = text
    )
}

@Composable
fun DisplayText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    text: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = textAlign,
        text = text,
    )
}