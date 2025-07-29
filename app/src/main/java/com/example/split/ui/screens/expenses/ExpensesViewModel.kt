package com.example.split.ui.screens.expenses

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.insert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import java.time.ZoneOffset
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

    val expenses = expensesRepo.getAllExpenses()


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
                    date = selectedDate ?: LocalDate.now(ZoneOffset.UTC).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli(),
                    paidByUserId = 1
                )
            )

        }
        _currentState = State.HOME
    }


}