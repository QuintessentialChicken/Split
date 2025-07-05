package com.example.split.ui.screens.expenses

import android.view.RoundedCorner
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.split.ui.screens.Debt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split.FabState
import com.example.split.TopBarState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExpensesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesViewModel = hiltViewModel(),
    setFab: (FabState?) -> Unit,
    setTopBar: (TopBarState?) -> Unit
) {
    LaunchedEffect(Unit) {
        setFab(
            FabState(
                Icons.Filled.Add,
                contentDescription = "Add Expense",
                onClick = { viewModel.currentState = State.ADD })
        )
    }

    BackHandler { viewModel.handleBackPress() }





    Column (
        modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ){
        when (viewModel.currentState) {
            State.HOME -> {
                setTopBar(null)
                Text(
                    fontSize = 24.sp,
                    text = "Paula Seidel",
                )
                Column {
                    ListItem(
                        headlineContent = { Text("Einkauf Edeka") },
                        supportingContent = { Text("Paula zahlte 53,33€") },
                        trailingContent = { Debt() },
                    )
                }
            }

            State.ADD -> {
                setTopBar(TopBarState("Add Expense"))
                AddExpense()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(modifier: Modifier = Modifier) {
    var description by remember { mutableStateOf("Description") }
    var amount by remember { mutableStateOf(0.00) }
    FlowRow(
        modifier = modifier.padding(bottom = 20.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Mit dir und: ")
        InputChip(selected = true, onClick = {}, label = { Text("Paula") })
    }
    TextField(modifier = modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp), value = description, onValueChange = {})
    TextField(modifier = modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp), value = amount.toString(), onValueChange = {})
    Button(
        modifier = modifier.fillMaxWidth().padding(horizontal = 50.dp, vertical = 5.dp),
        onClick = {}
    ) { Text("Gezahlt von dir und gleichmäßig geteilt")}
}