package com.example.split.ui.screens.expenses

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NoteAdd
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.split.FabState
import com.example.split.IconWrapper
import com.example.split.TopBarState
import com.example.split.TopBarType
import com.example.split.ui.components.DateIcon
import com.example.split.ui.components.DatePickerModal
import com.example.split.ui.components.Debt
import com.example.split.utils.CurrencyOutputTransformation
import com.example.split.utils.DigitOnlyInputTransformation
import com.example.split.utils.formatCurrency
import com.example.split.utils.millisToDateString


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

    BackHandler(enabled = viewModel.currentState == State.ADD) { viewModel.handleBackPress() }

    val expenses by viewModel.expenses.collectAsState(initial = emptyList())

    Column(
        modifier
            .fillMaxSize()
    ) {
        when (viewModel.currentState) {
            State.HOME -> {
                setTopBar(
                    TopBarState(
                        title = "Paula Seidel",
                        subtitle = "Paula Seidel schuldet dir ${viewModel.balance.firstOrNull()?.amount ?: "0.00€"}",
                        type = TopBarType.LARGE,
                        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                    )
                )
                setFab(
                    FabState(
                        Icons.Default.Add,
                        contentDescription = "Add Expense",
                        onClick = { viewModel.currentState = State.ADD }
                    )
                )
                Text("Paula Seidel schuldet dir ${viewModel.balance.firstOrNull()?.amount ?: "0.00€"}")

                LazyColumn {
                    var lastDate = "Jun 1970"
                    items(expenses) { expense ->
                        val currentDate = millisToDateString(expense.date, "MMMM yyyy")
                        if (currentDate != lastDate) {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp),
                                    color = Color.Gray
                                )

                                Text(
                                    text = currentDate,
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = Color.Gray,
                                )

                                HorizontalDivider(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(1.dp),
                                    color = Color.Gray
                                )
                            }
                        }
                        ListItem(
                            headlineContent = { Text(expense.title) },
                            supportingContent = { Text("Paula hat ${expense.amount}€ gezahlt") },
                            leadingContent = { DateIcon(timestamp = expense.date) },
                            trailingContent = {
                                Debt(
                                    amount = formatCurrency(
                                        expense.amount,
                                        expense.currencyCode
                                    )
                                )
                            },
                        )
                        lastDate = currentDate
                    }
                }
            }

            State.ADD -> {
                var title = rememberTextFieldState()
                var amount = rememberTextFieldState()
                setFab(null)
                setTopBar(
                    TopBarState(
                        title = "Add Expense",
                        actionIcon = IconWrapper(Icons.Default.Done, "Confirm Add") {
                            viewModel.confirmAdd(
                                title,
                                amount
                            )
                        })
                )
                AddExpense(title = title, amount = amount, onDateSelected = {
                    viewModel.selectedDate = it
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(
    title: TextFieldState,
    amount: TextFieldState,
    onDateSelected: (date: Long?) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            FlowRow(
                modifier = Modifier.padding(bottom = 20.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Mit dir und: ")
                InputChip(selected = true, onClick = {}, label = { Text("Paula") })
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 5.dp),
                label = { Text("Description") },
                state = title,
                placeholder = { Text("Enter a description") },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            TextField(
                modifier = Modifier
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 5.dp),
                onClick = {}
            ) {
                Text("Gezahlt von dir und gleichmäßig geteilt")
            }
        }
        Row(
            modifier = Modifier
                .imePadding()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AssistChip(
                modifier = Modifier.weight(1f),
                leadingIcon = { Icon(Icons.Default.GroupAdd, "Select a group") },
                onClick = {},
                label = { Text("Gruppe auswählen") }
            )
            AssistChip(
                leadingIcon = { Icon(Icons.Default.EditCalendar, "Add a date") },
                onClick = { showPicker = true },
                label = { Text("29. Juli") }
            )
            AssistChip(
                leadingIcon = { Icon(Icons.AutoMirrored.Outlined.NoteAdd, "Add a note") },
                onClick = {},
                label = { Text("Notiz") }
            )
        }
    }
    if (showPicker) {
        DatePickerModal({ onDateSelected(it) }) {
            showPicker = false
        }
    }
}