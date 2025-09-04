package com.example.split.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.split.data.Group
import com.example.split.navigation.Expenses
import com.example.split.utils.millisToDateString

@Composable
fun Debt(modifier: Modifier = Modifier, amount: String, owes: Boolean) {
    Column {
        if (owes) Text("Du schuldest:")
        else Text("Du leihst:")
        Text(text = amount, fontSize = 16.sp)
    }
}

@Composable
fun DateIcon(
    modifier: Modifier = Modifier,
    timestamp: Long
) {
    val dateString = millisToDateString(timestamp, "d MMM yyyy").split(" ")
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(modifier = Modifier.offset(y = 2.dp), text = dateString[0])
        Text(dateString[1])
    }
}


@Composable
fun GroupLazyList(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onClick: (route: String) -> Unit,
    groups: List<Group>
) {
    val isRefreshing = false

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh.invoke() },
    ) {
        LazyColumn (modifier = Modifier.fillMaxSize()){
            items(groups) { group ->
                ListItem(
                    modifier = modifier.clickable(onClick = { onClick(Expenses.route) }),
                    headlineContent = { Text(group.name) },
                    trailingContent = { Debt(amount = "10€", owes = true) },
                )
            }
        }
    }
}