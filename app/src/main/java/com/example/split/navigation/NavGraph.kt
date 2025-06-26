package com.example.split.navigation

import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.split.SplitAppState
import com.example.split.ui.screens.HomeScreen

fun NavGraphBuilder.setupNavGraph(
    appState: SplitAppState
) {
    composable(Home.route) {
        HomeScreen()
    }
}