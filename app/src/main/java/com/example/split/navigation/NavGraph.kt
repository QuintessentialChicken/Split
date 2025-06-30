package com.example.split.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.split.SplitAppState
import com.example.split.ui.screens.AccountScreen
import com.example.split.ui.screens.FriendsScreen
import com.example.split.ui.screens.GroupsScreen

fun NavGraphBuilder.setupNavGraph(
    appState: SplitAppState
) {
    composable(Groups.route) {
        GroupsScreen()
    }

    composable(Friends.route) {
        FriendsScreen()
    }

    composable(Account.route) {
        AccountScreen()
    }
}