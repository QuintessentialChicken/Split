package com.example.split

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.split.navigation.Destinations
import com.example.split.navigation.Groups

class SplitAppState (
    val navController: NavHostController,
) {
    val fabState = mutableStateOf<FabState?>(null)
    val topBarState = mutableStateOf<TopBarState?>(null)
    var currentScreen: Destinations = Groups

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    //Pops up the back stack until popUp is found before navigating to route
    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = false }
        }
    }

    //Clears the back stack before navigating to route
    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}

data class FabState(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)

enum class TopBarType {
    CENTER,
    SMALL,
    MEDIUM,
    LARGE
}

data class IconWrapper(
    val icon: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)

data class TopBarState @OptIn(ExperimentalMaterial3Api::class) constructor(
    var title: String = "",
    var type: TopBarType = TopBarType.CENTER,
    var navIcon: IconWrapper? = null,
    var scrollBehavior: TopAppBarScrollBehavior? = null,
    var actionIcon: IconWrapper? = null,
)
