package com.example.split.ui.screens.expenses

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.Expense
import com.example.split.data.ExpensesRepository
import com.example.split.data.Group
import com.example.split.data.User
import com.example.split.data.UsersRepository
import com.example.split.utils.formatCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject



data class UserBalance(
    val userId: Long,
    val amount: String
)

data class UIExpense(
    val title: String,
    val paidBy: String,
    val amountPaid: String,
    val amountOwed: String,
    val owes: Boolean,
    val paidOn: Long,
)

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expensesRepo: ExpensesRepository,
    private val userRepo: UsersRepository
) : ViewModel() {
    enum class UiState {
        HOME,
        ADD
    }

    val userId = "1" // TODO Replace with actual userId

    private var _currentUiState by mutableStateOf(UiState.HOME)
    var currentUiState: UiState
        get() = _currentUiState
        set(value) {
            _currentUiState = value
        }

    internal var selectedDate: Long? = null
    var selectedUsers = mutableListOf<User>()
    var payer = User("-1", "")

    private var _balance by mutableStateOf<List<UserBalance>>(emptyList())
    var balance: List<UserBalance>
        get() = _balance
        set(value) {
            _balance = value
        }

    var _uiExpenses by mutableStateOf<List<UIExpense>>(emptyList())
    var uiExpenses: List<UIExpense>
        get() = _uiExpenses
        set(value) {
            _uiExpenses = value
        }

    val expenses: StateFlow<List<Expense>> = expensesRepo.getExpensesByGroup("A2zZCcXnbNy1iQVdcKA8").stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _options = listOf(User("2", "Paula"), User("3", "Test"))

    private var _filteredOptions = mutableStateListOf<User>()
    var filteredOptions: SnapshotStateList<User>
        get() = _filteredOptions
        set(value) {
            _filteredOptions = value
        }


    fun filterText(input: String) {
        _filteredOptions.clear()
        _filteredOptions.addAll(
            if (input.isBlank()) _options
            else _options.filter { it.name.contains(input, ignoreCase = true) }
        )
    }

    fun handleBackPress() {
        _currentUiState = UiState.HOME
    }

    fun confirmAdd(
        title: TextFieldState,
        amount: TextFieldState
    ) {
        viewModelScope.launch {
            val date = if (selectedDate != null) selectedDate!! + (Instant.now()
                .toEpochMilli() - ZonedDateTime.now(ZoneOffset.UTC).toLocalDate()
                .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()) else Instant.now()
                .toEpochMilli()
            println(amount.text.toString())

            expensesRepo.addExpenseToGroup(
                Expense(
                    title = title.text.toString(),
                    amount = amount.text.toString().toInt(),
                    currencyCode = "EUR",
                    date = date,
                    paidByUserId = userId
                ),
                "1"
            )
            selectedDate = null
            selectedUsers.clear()
        }
        _currentUiState = UiState.HOME
    }


    fun addGroup(
        group: Group
    ) {
        viewModelScope.launch {

            expensesRepo.addGroup(group)
        }
    }
}