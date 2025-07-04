package com.example.split

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.split.navigation.Expenses
import com.example.split.navigation.Groups
import com.example.split.navigation.setupNavGraph
import com.example.split.ui.components.BottomBar
import com.example.split.ui.components.TopBar
import com.example.split.ui.theme.SplitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplitTheme {
                val appState = rememberAppState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.surface,
//                    topBar = {
//                        TopBar(
//                            appState
//                        ) { route -> appState.navigate(route) }
//                    },
                    floatingActionButton = {
                        appState.fabState.value?.let { state ->
                            FloatingActionButton(onClick = state.onClick) {
                                Icon(state.icon, contentDescription = state.contentDescription)
                            }
                        }
                    },
                    bottomBar = { BottomBar(appState) }
                ) { innerPadding ->
                    NavHost(
                        navController = appState.navController,
                        startDestination = Groups.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        setupNavGraph(appState)
                    }

                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) =
    remember(
        navController,
    ) {
        SplitAppState(
            navController,
        )
    }