package com.example.split.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.split.FabState
import com.example.split.SplitAppState
import com.example.split.ui.screens.AccountScreen
import com.example.split.ui.screens.ExpensesScreen
import com.example.split.ui.screens.FriendsScreen
import com.example.split.ui.screens.GroupsScreen

fun NavGraphBuilder.setupNavGraph(
    appState: SplitAppState
) {
    composable(Groups.route) {

        LaunchedEffect(Unit) {
            appState.fabState.value = null
        }
        GroupsScreen(navigate = { route -> appState.navigate(route)})
    }

    composable(Friends.route) {
        FriendsScreen()
    }

    composable(Account.route) {
        AccountScreen()
    }

    composable(Expenses.route) {
        LaunchedEffect(Unit) {
            appState.fabState.value = FabState(Icons.Filled.Add, contentDescription = "Add Expense", onClick = {})
        }
        ExpensesScreen()
    }
}