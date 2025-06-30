package com.example.split.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun GroupsScreen(modifier: Modifier = Modifier) {
    Column  {
        ListItem(
            headlineContent = { Text("Paula") },
            trailingContent = { Debt() },
        )
        ListItem(
            headlineContent = { Text("Paul") },
            trailingContent = { Debt() }
        )
        ListItem(
            headlineContent = { Text("Christoph") },
            trailingContent = { Debt() }
        )
    }
}

@Composable
fun Debt(modifier: Modifier = Modifier) {
    Column {
        Text("Du schuldest:")
        Text(text = "10€", fontSize = 16.sp)
    }
}