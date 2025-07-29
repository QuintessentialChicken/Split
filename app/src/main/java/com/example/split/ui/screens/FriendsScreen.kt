package com.example.split.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.split.IconWrapper
import com.example.split.TopBarState
import com.example.split.navigation.Expenses

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit,
    setTopBar: (TopBarState?) -> Unit
) {
    setTopBar(TopBarState(title = "Friends", actionIcon = IconWrapper(Icons.Default.GroupAdd, contentDescription = "Add a Friend", {})))
    Column  (modifier = modifier.padding(horizontal = 10.dp)) {
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