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
import com.example.split.data.Participant
import com.example.split.data.User
import com.example.split.data.UsersRepository
import com.example.split.utils.formatCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.exp

enum class State {
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

    var _uiExpenses by mutableStateOf<List<UIExpense>>(emptyList())
    var uiExpenses: List<UIExpense>
        get() = _uiExpenses
        set(value) {
            _uiExpenses = value
        }

    val expenses = expensesRepo.getAllSortedByDateDesc()
    val participants = expensesRepo.getAllParticipants()
    val users = userRepo.getAll()

    private val chipOptions = listOf("Option1, Option2, Option3")
    private val _filteredOptions = MutableStateFlow(chipOptions)
    var filteredOptions: StateFlow<List<String>> = _filteredOptions


    init {
        viewModelScope.launch {
            combine(
                expenses,
                participants,
                users
            ) { expenses, participants, users ->
                val totalBalance = calculateBalance(expenses, participants)
                val debtPerExpense = calculatePerExpense(expenses, participants, users)

                totalBalance to debtPerExpense
            }.collect { (totalBalance, deptPerExpense) ->
                _balance = totalBalance
                    .map { (userId, amount) -> UserBalance(userId, formatCurrency(amount)) }
                    .sortedByDescending { it.amount }
                _uiExpenses = deptPerExpense
            }
        }
    }

    fun filterText(input: String) {
        // This filter returns the full items list when input is an empty string.
        _filteredOptions.value = chipOptions.filter { it.contains(input, ignoreCase = true) }
    }

    fun handleBackPress() {
        _currentState = State.HOME
    }

    fun confirmAdd(
        title: TextFieldState,
        amount: TextFieldState
    ) {
        viewModelScope.launch {
            val date = if (selectedDate != null) selectedDate!! + (Instant.now().toEpochMilli() - ZonedDateTime.now(ZoneOffset.UTC).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()) else Instant.now().toEpochMilli()
            println(amount.text.toString())
            userRepo.addUser(User(1, "Leon"))
            userRepo.addUser(User(2, "Paula"))
            expensesRepo.addExpense(
                Expense(
                    title = title.text.toString(),
                    amount = amount.text.toString().toInt(),
                    currencyCode = "EUR",
                    date = date,
                    paidByUserId = 1
                ),
                listOf(
                    Participant(1, 0.5), Participant(2, 0.5)
                )
            )
        }
        selectedDate = null
        _currentState = State.HOME
    }




    fun calculatePerExpense(
        expenses: List<Expense>,
        participants: List<ExpenseParticipant>,
        users: List<User>
    ): List<UIExpense> {
        val result = mutableListOf<UIExpense>()
        for (expense in expenses) {
            val share = participants.find { participant -> participant.userId == userId }?.share ?: 0.0
            result.add(UIExpense(
                title = expense.title,
                paidBy = users.find { user -> user.userId == expense.paidByUserId }?.name ?: "",
                amountPaid = formatCurrency(expense.amount, expense.currencyCode),
                amountOwed = if (expense.paidByUserId == userId) formatCurrency((expense.amount * share).toInt(), expense.currencyCode) else formatCurrency(expense.amount - (-share * expense.amount).toInt(), expense.currencyCode),
                owes = expense.paidByUserId != userId,
                paidOn = expense.date
            ))
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
                    balances[participant.userId] = (balances.getOrDefault(participant.userId, 0) + (participant.share * expense.amount)).toInt()
                }

                if (participant.userId == userId && expense.paidByUserId != userId) {
                    // They paid for you → you owe them
                    balances[expense.paidByUserId] = balances.getOrDefault(expense.paidByUserId, 0) - (participant.share * expense.amount).toInt()
                }
            }
        }
        return balances
    }

    fun calculateOwedByCurrentUser(
        expense: Expense,
        participants: List<ExpenseParticipant>,
        currentUserId: Long
    ): Double {
        val currentUserShare = participants.find { it.userId == currentUserId }?.share ?: 0.0
        if (currentUserShare == 0.0) return -1.0 // User is not involved, early exit

        val userPaid = expense.paidByUserId == currentUserId

        return if (userPaid) {
            // User paid — subtract their own share to get what others owe them
            val owedByOthers = 1 - currentUserShare
            -owedByOthers  // Negative because others owe you
        } else {
            // User did not pay — they owe the payer
            currentUserShare
        }
    }


}