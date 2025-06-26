package com.example.split.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.split.ui.screens.HomeScreen
import com.example.split.ui.screens.TestScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Home)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is Home -> {
                    NavEntry(
                        key = key
                    ) {
                        HomeScreen(
                            onButtonClick = {
                                backStack.addLast(Test)
                            }
                        )
                    }
                }
                is Test -> {
                    NavEntry(
                        key = key
                    ) {
                        TestScreen()
                    }
                }
                else -> throw RuntimeException("Invalid NavKey")
            }
        }
    )
}