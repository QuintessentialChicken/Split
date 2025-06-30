package com.example.split

import android.util.Log
import androidx.navigation.NavHostController
import com.example.split.navigation.Destinations
import com.example.split.navigation.Groups

class SplitAppState (
    val navController: NavHostController,
) {
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

