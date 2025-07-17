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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class State {
    HOME,
    ADD
}

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val repository: ExpensesRepository
) : ViewModel() {

    private var _currentState by mutableStateOf(State.HOME)
    var currentState: State
        get() = _currentState
        set(value) {
            _currentState = value
            println(_currentState)
        }

    val expenses = repository.getAllExpenses()//.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun handleBackPress() {
        when (_currentState) {
            State.ADD -> _currentState = State.HOME
            State.HOME -> {}
        }
    }

    fun confirmAdd(
        title: TextFieldState,
        amount: TextFieldState
    ) {
        amount.edit { insert(this.length - 2, ".") }
        viewModelScope.launch {
            repository.addExpense(
                Expense(
                    title = title.text.toString(),
                    amount = amount.text.toString().toDouble(),
                    paidBy = "Ich"
                )
            )
        }
        _currentState = State.HOME
    }


}