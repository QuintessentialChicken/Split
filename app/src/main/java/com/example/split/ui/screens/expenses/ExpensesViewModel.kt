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
import com.example.split.navigation.Expenses
import com.example.split.utils.formatCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.collections.map
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


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

data class ExpensesUiState(
    val expenses: List<UIExpense>,
    val totalOwed: Int
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

    val expensesUiState: StateFlow<ExpensesUiState> = expensesRepo.getExpensesByGroup("A2zZCcXnbNy1iQVdcKA8")
        .map { expenses ->
            val currentUser = userRepo.getCurrentUserId()
            var total = 0

            val uiList = expenses.map { expense ->
                val share = expense.participants[currentUser] ?: 0.0f
                val paidByCurrentUser = expense.paidByUserId == userRepo.getCurrentUserId()
                val rawAmountOwed = if (paidByCurrentUser) {
                    expense.amount - (expense.amount * share)
                } else {
                    -expense.amount * share
                }
                val amountOwed = rawAmountOwed.roundToInt()
                total += amountOwed

                UIExpense(
                    title = expense.title,
                    paidBy = userRepo.getUserById(expense.paidByUserId)?.name ?: "",
                    amountPaid = formatCurrency(expense.amount),
                    amountOwed = formatCurrency(amountOwed.absoluteValue),
                    owes = amountOwed < 0,
                    paidOn = expense.date,
                )
            }

            ExpensesUiState(
                expenses = uiList,
                totalOwed = total
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ExpensesUiState(emptyList(), 0))

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