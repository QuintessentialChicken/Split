package com.example.split.ui.screens.expenses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

enum class State {
    HOME,
    ADD
}

class ExpensesViewModel() : ViewModel() {

    private var _currentState by mutableStateOf(State.HOME)
    var currentState: State
        get() = _currentState
        set(value) {
            _currentState = value
            println(_currentState)
        }

    fun handleBackPress() {
        when (_currentState) {
            State.ADD -> _currentState = State.HOME
            State.HOME -> {}
        }
    }
}