package com.example.split.ui.screens.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split.FabState
import com.example.split.IconWrapper
import com.example.split.TopBarState
import com.example.split.navigation.Expenses
import com.example.split.ui.components.Debt
import com.example.split.ui.components.GroupLazyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    viewModel: GroupsViewModel = hiltViewModel(),
    navigate: (String, String) -> Unit,
    setTopBar: (TopBarState?) -> Unit,
    setFab: (FabState?) -> Unit,
) {
    setTopBar(TopBarState(title = "Groups", actionIcon = IconWrapper(Icons.Default.GroupAdd, contentDescription = "Add a Friend", {})))
    setFab(null)

    val groups by viewModel.groups.collectAsState(initial = emptyList())

    GroupLazyList(
        onClick = { route, id -> navigate(route, id) },
        groups = groups,
    )
}

