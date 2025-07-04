package com.example.split.ui.screens.expenses

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.split.ui.screens.Debt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split.FabState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExpensesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel(),
    setFab: (FabState?) -> Unit
) {
    LaunchedEffect(Unit) {
        setFab(
            FabState(Icons.Filled.Add, contentDescription = "Add Expense", onClick = { viewModel.currentState = State.ADD })
        )
    }
    when (viewModel.currentState) {
        State.HOME -> {
            Column {
                Text(
                    fontSize = 24.sp,
                    text = "Paula Seidel",
                )
                Column {
                    ListItem(
                        headlineContent = { Text("Einkauf Edeka") },
                        supportingContent = { Text("Paula zahlte 53,33€") },
                        trailingContent = { Debt() },
                    )
                }
            }
        }
        State.ADD -> {
            AddExpense()
        }
    }
}

@Composable
fun AddExpense(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Add Expense")
    }
}