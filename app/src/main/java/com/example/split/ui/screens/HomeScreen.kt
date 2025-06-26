package com.example.split.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Button(onButtonClick) {
        Text(
            text = "Hello Paula!",
            modifier = modifier
        )
    }
}