package com.example.split.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.split.navigation.Expenses

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit
) {
    Column  {
        ListItem(
            modifier = modifier.clickable(onClick = {navigate(Expenses.route)}),
            headlineContent = { Text("Paula") },
            trailingContent = { Debt(amount = "10€") },
        )
        ListItem(
            headlineContent = { Text("Paul") },
            trailingContent = { Debt(amount = "10€") }
        )
        ListItem(
            headlineContent = { Text("Christoph") },
            trailingContent = { Debt(amount = "10€") }
        )
    }
}

@Composable
fun Debt(modifier: Modifier = Modifier, amount: String) {
    Column {
        Text("Du schuldest:")
        Text(text = amount, fontSize = 16.sp)
    }
}