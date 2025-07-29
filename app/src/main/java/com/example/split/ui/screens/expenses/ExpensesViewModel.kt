package com.example.split.ui.screens.expenses

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.insert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.Expense
import com.example.split.data.ExpensesRepository
import com.example.split.data.User
import com.example.split.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val expenses = expensesRepo.getAllSortedByDateDesc()


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


}