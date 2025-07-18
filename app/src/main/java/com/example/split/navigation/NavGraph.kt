package com.example.split.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.split.FabState
import com.example.split.SplitAppState
import com.example.split.ui.screens.AccountScreen
import com.example.split.ui.screens.expenses.ExpensesScreen
import com.example.split.ui.screens.FriendsScreen
import com.example.split.ui.screens.GroupsScreen

fun NavGraphBuilder.setupNavGraph(
    appState: SplitAppState
) {
    composable(Groups.route) {
        appState.fabState.value = null
        GroupsScreen(
            navigate = { route -> appState.navigate(route) },
            setTopBar = { appState.topBarState.value = it }
        )
    }

    composable(Friends.route) {
        appState.fabState.value = null
        appState.topBarState.value = null
        FriendsScreen()
    }

    composable(Account.route) {
        appState.fabState.value = null
        appState.topBarState.value = null
        AccountScreen()
    }

    composable(Expenses.route) {
        ExpensesScreen(
            setFab = { appState.fabState.value = it },
            setTopBar = { appState.topBarState.value = it }
        )
    }
}