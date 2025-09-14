package com.example.split.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.split.SplitAppState
import com.example.split.ui.screens.AccountScreen
import com.example.split.ui.screens.friends.FriendsScreen
import com.example.split.ui.screens.groups.GroupsScreen
import com.example.split.ui.screens.expenses.ExpensesScreen

fun NavGraphBuilder.setupNavGraph(
    appState: SplitAppState
) {
    composable(Groups.route) {
        GroupsScreen(
            navigate = { route, id -> appState.navigate("${route}/${id}") },
            setTopBar = { appState.topBarState.value = it },
            setFab = { appState.fabState.value = it }
        )
    }

    composable(
        route = Friends.route,
    ) {
        FriendsScreen(
            navigate = { route, id -> appState.navigate("${route}/${id}") },
            setTopBar = { appState.topBarState.value = it },
            setFab = { appState.fabState.value = it }
        )
    }

    composable(Account.route) {
        AccountScreen(
            setTopBar = { appState.topBarState.value = it },
            setFab = { appState.fabState.value = it }
        )
    }

    composable(
        route = Expenses.routeWithArgs,
        arguments = listOf(navArgument("groupId") { type = NavType.StringType })
    ) {
        ExpensesScreen(
            setFab = { appState.setFabState(it) },
            setTopBar = { appState.topBarState.value = it },
        )
    }
}