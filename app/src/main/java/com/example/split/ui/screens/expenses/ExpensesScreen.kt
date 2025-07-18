package com.example.split.ui.screens.expenses

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split.FabState
import com.example.split.IconWrapper
import com.example.split.TopBarState
import com.example.split.ui.screens.Debt
import com.example.split.utils.CurrencyOutputTransformation
import com.example.split.utils.DigitOnlyInputTransformation
import org.w3c.dom.Text
import kotlin.math.exp

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
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
                Icons.Default.Add,
                contentDescription = "Add Expense",
                onClick = { viewModel.currentState = State.ADD })
        )
    }

    BackHandler { viewModel.handleBackPress() }

    val expenses by viewModel.expenses.collectAsState(initial = emptyList())

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        when (viewModel.currentState) {
            State.HOME -> {
                setTopBar(TopBarState("Paula Seidel"))
                Column {
                    expenses.forEach { expense ->
                        ListItem(
                            headlineContent = { Text(expense.title) },
                            supportingContent = { Text("Paula hat ${expense.amount}€ gezahlt") },
                            trailingContent = { Debt(amount = expense.amount.toString() + "€") },
                        )
                    }
                }
            }

            State.ADD -> {
                var title = rememberTextFieldState()
                var amount = rememberTextFieldState()
                setFab(null)
                setTopBar(TopBarState("Add Expense", actionIcon = IconWrapper(Icons.Default.Add, "Confirm Add") {
                    viewModel.confirmAdd(
                        title,
                        amount
                    )
                }))
                AddExpense(title = title, amount = amount)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(
    modifier: Modifier = Modifier,
    title: TextFieldState,
    amount: TextFieldState
) {

    FlowRow(
        modifier = modifier.padding(bottom = 20.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Mit dir und: ")
        InputChip(selected = true, onClick = {}, label = { Text("Paula") })
    }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp),
        label = { Text("Description") },
        state = title,
        placeholder = { Text("Enter a description") }
    )
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp),
        label = { Text("Amount") },
        state = amount,
        placeholder = { Text("0,00€") },
        inputTransformation = DigitOnlyInputTransformation(),
        outputTransformation = CurrencyOutputTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp, vertical = 5.dp),
        onClick = {}
    ) {
        Text("Gezahlt von dir und gleichmäßig geteilt")
    }
}