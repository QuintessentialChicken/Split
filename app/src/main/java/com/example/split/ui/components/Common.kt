package com.example.split.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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