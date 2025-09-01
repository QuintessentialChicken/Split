package com.example.split

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.split.navigation.Expenses
import com.example.split.navigation.Friends
import com.example.split.navigation.setupNavGraph
import com.example.split.ui.components.BottomBar
import com.example.split.ui.theme.SplitTheme
import dagger.hilt.android.AndroidEntryPoint


val TESTING = false

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplitTheme {
                val appState = rememberAppState()

                Scaffold(
                    modifier = appState.topBarState.value?.scrollBehavior?.let { scrollBehavior -> Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)} ?: Modifier,
                    containerColor = MaterialTheme.colorScheme.surface,
                    topBar = {
                        appState.topBarState.value?.let { state ->
                            CreateTopBar(state = state, appState = appState)
                        }
                    },
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
                        startDestination = Friends.route,
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
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
) = remember(
    navController,
) {
    SplitAppState(
        navController,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateTopBar(
    modifier: Modifier = Modifier,
    state: TopBarState,
    appState: SplitAppState
) {
    when (state.type) {
        TopBarType.CENTER -> {
            CenterAlignedTopAppBar(
                title = { Text(state.title) },
                navigationIcon = { state.navIcon?.let { navIcon -> TopBarIcon(iconWrapper = navIcon) } },
                actions = { state.actionIcon?.let { actionIcon -> TopBarIcon(iconWrapper = actionIcon) } },
                scrollBehavior = state.scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, subtitleContentColor = MaterialTheme.colorScheme.onPrimary, scrolledContainerColor = MaterialTheme.colorScheme.primary)
            )
        }
        TopBarType.SMALL -> {
            TopAppBar(
                title = { Text(state.title) },
                subtitle = { state.subtitle?.let { subtitle -> Text(subtitle)}},
                navigationIcon = { state.navIcon?.let { navIcon -> TopBarIcon(iconWrapper = navIcon) } },
                actions = { state.actionIcon?.let { actionIcon -> TopBarIcon(iconWrapper = actionIcon) } },
                scrollBehavior = state.scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, subtitleContentColor = MaterialTheme.colorScheme.onPrimary, scrolledContainerColor = MaterialTheme.colorScheme.primary)
            )
        }
        TopBarType.MEDIUM -> {
            MediumFlexibleTopAppBar(
                title = { Text(state.title) },
                subtitle = { state.subtitle?.let { subtitle -> Text(subtitle)}},
                navigationIcon = { state.navIcon?.let { navIcon -> TopBarIcon(iconWrapper = navIcon) } },
                actions = { state.actionIcon?.let { actionIcon -> TopBarIcon(iconWrapper = actionIcon) } },
                scrollBehavior = state.scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, subtitleContentColor = MaterialTheme.colorScheme.onPrimary, scrolledContainerColor = MaterialTheme.colorScheme.primary)
            )
        }
        TopBarType.LARGE -> {
            LargeFlexibleTopAppBar(
                title = { Text(state.title, maxLines = 1) },
                subtitle = { state.subtitle?.let { subtitle -> Text(subtitle)}},
                navigationIcon = { state.navIcon?.let { navIcon -> TopBarIcon(iconWrapper = navIcon) } },
                actions = { state.actionIcon?.let { actionIcon -> TopBarIcon(iconWrapper = actionIcon) } },
                scrollBehavior = state.scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, subtitleContentColor = MaterialTheme.colorScheme.onPrimary, scrolledContainerColor = MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun TopBarIcon(
    modifier: Modifier = Modifier,
    iconWrapper: IconWrapper
) {
    IconButton(onClick = iconWrapper.onClick) {
        Icon(
            iconWrapper.icon,
            contentDescription = iconWrapper.contentDescription
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ChristianeDebtScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeFlexibleTopAppBar(
                title = {
                    Text("Christiane", maxLines = 1)
                },
                subtitle = {
                    Text("Christiane schuldet dir 200,00€")
                },
                navigationIcon = {
                    IconButton(onClick = { /* handle back */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* handle settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Christiane", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Du schuldest Christiane 1.135,77 €",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Schulden begleichen */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
                        ) {
                            Text("Schulden begleichen")
                        }
                        OutlinedButton(onClick = { /* Erinnern */ }) {
                            Text("Erinnern…")
                        }
                        OutlinedButton(onClick = { /* Statistik */ }) {
                            Icon(
                                Icons.Default.AcUnit, // Replace with real icon
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Statistik")
                        }
                    }
                }
            }

            // Add more items here as needed
        }
    }
}