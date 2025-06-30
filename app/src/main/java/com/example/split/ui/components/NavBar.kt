package com.example.split.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.split.SplitAppState
import com.example.split.navigation.Account
import com.example.split.navigation.Destinations
import com.example.split.navigation.Friends
import com.example.split.navigation.Groups


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    appState: SplitAppState,
    actionsNavigate: (String) -> Unit
) {
    var currentScreen by remember {mutableStateOf(appState.currentScreen)}
    TopAppBar (
        title = {
            DisplayText(text = currentScreen.title)
        },
//        actions = {
//            IconButton(
//                onClick = {
//                    actionsNavigate(Settings.route)
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Settings,
//                    contentDescription = "Settings"
//                )
//            }
//        }
    )
}

@Composable
fun BottomBar(
    appState: SplitAppState
) {
    val screens = listOf(
        Groups,
        Friends,
        Account
    )
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                appState = appState
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Destinations,
    currentDestination: NavDestination?,
    appState: SplitAppState
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            appState.navigateAndPopUp(screen.route, Groups.route)
        }
    )
}