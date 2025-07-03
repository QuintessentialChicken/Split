package com.example.split.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.split.ui.components.DisplayText

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier) {
    Column {
        Text(
            fontSize = 24.sp,
            text = "Paula Seidel",
        )
        Column {
            ListItem(
                headlineContent = { Text("Einkauf Edeka") },
                supportingContent = { Text("Paula zahlte 53,33€")},
                trailingContent = { Debt() },
            )
        }
    }

}