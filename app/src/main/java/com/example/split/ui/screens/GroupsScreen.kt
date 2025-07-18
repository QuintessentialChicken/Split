package com.example.split.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.split.IconWrapper
import com.example.split.TopBarState
import com.example.split.navigation.Expenses

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit,
    setTopBar: (TopBarState?) -> Unit
) {
    setTopBar(TopBarState(title = "Groups", actionIcon = IconWrapper(Icons.Default.GroupAdd, contentDescription = "Add a Friend", {})))
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