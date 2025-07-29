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
import java.time.LocalDate
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

    private var _currentState by mutableStateOf(State.ADD)
    var currentState: State
        get() = _currentState
        set(value) {
            _currentState = value
            println(_currentState)
        }

    val expenses = expensesRepo.getAllExpenses()//.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun handleBackPress() {
        _currentState = State.HOME
    }

    fun confirmAdd(
        title: TextFieldState,
        amount: TextFieldState
    ) {
        amount.edit { insert(this.length - 2, ".") }
        viewModelScope.launch {
            userRepo.addUser(User(2, "Paula"))
            expensesRepo.addExpense(
                Expense(
                    title = title.text.toString(),
                    amount = amount.text.toString().toDouble(),
                    date = LocalDate.now().toEpochDay(),
                    paidByUserId = 1
                )
            )
        }
        _currentState = State.HOME
    }


}