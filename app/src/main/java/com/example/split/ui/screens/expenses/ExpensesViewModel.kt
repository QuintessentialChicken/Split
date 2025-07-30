package com.example.split.ui.screens.expenses

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.insert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.Expense
import com.example.split.data.ExpenseParticipant
import com.example.split.data.ExpensesRepository
import com.example.split.data.User
import com.example.split.data.UsersRepository
import com.example.split.utils.formatCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject

enum class State {
    HOME,
    ADD
}

data class UserBalance(
    val userId: Long,
    val amount: String
)

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expensesRepo: ExpensesRepository,
    private val userRepo: UsersRepository
) : ViewModel() {

    private var _currentState by mutableStateOf(State.HOME)
    var currentState: State
        get() = _currentState
        set(value) {
            _currentState = value
        }

    internal var selectedDate: Long? = null

    private var _balance by mutableStateOf<List<UserBalance>>(emptyList())
    var balance: List<UserBalance>
        get() = _balance
        set(value) {
            _balance = value
        }

    val expenses = expensesRepo.getAllSortedByDateDesc()
    val participants = expensesRepo.getAllParticipants()

    init {
        viewModelScope.launch {
            combine(
                expenses,
                participants
            ) { expenses, participants ->
                calculateBalance(expenses, participants)
            }.collect { newBalance ->
                _balance = newBalance
                    .map { (userId, amount) -> UserBalance(userId, formatCurrency(amount)) }
                    .sortedByDescending { it.amount }
            }
        }
    }


    fun handleBackPress() {
        _currentState = State.HOME
    }

    fun confirmAdd(
        title: TextFieldState,
        amount: TextFieldState
    ) {
        amount.edit { insert(this.length - 2, ".") }
        viewModelScope.launch {
            val date = if (selectedDate != null) selectedDate!! + (Instant.now().toEpochMilli() - ZonedDateTime.now(ZoneOffset.UTC).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()) else Instant.now().toEpochMilli()
            println("Selected Date: $selectedDate")
            println(date)
            userRepo.addUser(User(1, "Leon"))
            expensesRepo.addExpense(
                Expense(
                    title = title.text.toString(),
                    amount = amount.text.toString().toDouble(),
                    currencyCode = "EUR",
                    date = date,
                    paidByUserId = 1
                )
            )

        }
        selectedDate = null
        _currentState = State.HOME
    }

    val userId = 1L // TODO Replace with actual userId
    fun calculateBalance(
        expenses: List<Expense>,
        participants: List<ExpenseParticipant>
    ): Map<Long, Double> {

        // Group participants by expenseId for fast lookup
        val partsByExpense = participants.groupBy { it.expenseId }

        val balances = mutableMapOf<Long, Double>()

        for (expense in expenses) {
            println(partsByExpense)
            val parts = partsByExpense[expense.expenseId] ?: continue
            for (participant in parts) {
                if (expense.paidByUserId == userId && participant.userId != userId) {
                    // You paid for them → they owe you
                    balances[participant.userId] = balances.getOrDefault(participant.userId, 0.0) + participant.share * expense.amount
                }

                if (participant.userId == userId && expense.paidByUserId != userId) {
                    // They paid for you → you owe them
                    balances[expense.paidByUserId] = balances.getOrDefault(expense.paidByUserId, 0.0) - participant.share * expense.amount
                }
            }
        }
        return balances
    }


}