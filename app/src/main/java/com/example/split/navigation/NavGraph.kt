package com.example.split.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.split.SplitAppState
import com.example.split.ui.screens.AccountScreen
import com.example.split.ui.screens.FriendsScreen
import com.example.split.ui.screens.GroupsScreen
import com.example.split.ui.screens.expenses.ExpensesScreen

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
        FriendsScreen(
            navigate = { route -> appState.navigate(route) },
            setTopBar = { appState.topBarState.value = it }
        )
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