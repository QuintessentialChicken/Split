package com.example.split.ui.screens.expenses

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.Expense
import com.example.split.data.ExpenseParticipant
import com.example.split.data.ExpensesRepository
import com.example.split.data.FirestoreGroup
import com.example.split.data.Participant
import com.example.split.data.User
import com.example.split.data.UsersRepository
import com.example.split.utils.formatCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject

enum class UiState {
    HOME,
    ADD
}

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

    val userId = 1L // TODO Replace with actual userId

    private var _currentUiState by mutableStateOf(UiState.ADD)
    var currentUiState: UiState
        get() = _currentUiState
        set(value) {
            _currentUiState = value
        }

    internal var selectedDate: Long? = null
    var selectedUsers = mutableListOf<User>()
    var payer = User(-1, "")

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

    private val _options = listOf(User(2, "Paula"), User(3, "Test"))

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
        group: FirestoreGroup
    ) {
        viewModelScope.launch {

            expensesRepo.addGroup(group)
        }
    }
    fun calculatePerExpense(
        expenses: List<Expense>,
        participants: List<ExpenseParticipant>,
        users: List<User>
    ): List<UIExpense> {
        val result = mutableListOf<UIExpense>()
        for (expense in expenses) {
            val share =
                participants.find { participant -> participant.userId == userId }?.share ?: 0.0
            result.add(
                UIExpense(
                    title = expense.title,
                    paidBy = users.find { user -> user.userId == expense.paidByUserId }?.name ?: "",
                    amountPaid = formatCurrency(expense.amount, expense.currencyCode),
                    amountOwed = if (expense.paidByUserId == userId) formatCurrency(
                        (expense.amount * share).toInt(),
                        expense.currencyCode
                    ) else formatCurrency(
                        expense.amount - (-share * expense.amount).toInt(),
                        expense.currencyCode
                    ),
                    owes = expense.paidByUserId != userId,
                    paidOn = expense.date
                )
            )
        }
        return result
    }

    fun calculateBalance(
        expenses: List<Expense>,
        participants: List<ExpenseParticipant>
    ): Map<Long, Int> {

        // Group participants by expenseId for fast lookup
        val partsByExpense = participants.groupBy { it.expenseId }

        val balances = mutableMapOf<Long, Int>()

        for (expense in expenses) {
            val parts = partsByExpense[expense.expenseId] ?: continue
            for (participant in parts) {
                if (expense.paidByUserId == userId && participant.userId != userId) {
                    // You paid for them → they owe you
                    balances[participant.userId] = (balances.getOrDefault(
                        participant.userId,
                        0
                    ) + (participant.share * expense.amount)).toInt()
                }

                if (participant.userId == userId && expense.paidByUserId != userId) {
                    // They paid for you → you owe them
                    balances[expense.paidByUserId] = balances.getOrDefault(
                        expense.paidByUserId,
                        0
                    ) - (participant.share * expense.amount).toInt()
                }
            }
        }
        return balances
    }
}