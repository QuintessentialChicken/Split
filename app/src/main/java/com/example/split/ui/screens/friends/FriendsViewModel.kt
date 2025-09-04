package com.example.split.ui.screens.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.ExpensesRepository
import com.example.split.data.Group
import com.example.split.data.User
import com.example.split.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val expensesRepo: ExpensesRepository,
    private val userRepo: UsersRepository
) : ViewModel() {
    enum class UiState {
        HOME,
        ADD
    }

    private var _currentUiState by mutableStateOf(UiState.HOME)
    var currentUiState: UiState
        get() = _currentUiState
        set(value) {
            _currentUiState = value
        }

    private val _friends = MutableStateFlow<List<Group>>(emptyList())
    val friends: StateFlow<List<Group>> = _friends

    fun loadFriends(userId: String) {
        println("REFRESHING")
        viewModelScope.launch {
            val result = expensesRepo.getFriendsByUserId(userId)
            _friends.value = result
        }.invokeOnCompletion {
            println(_friends.value)
        }
    }

    fun DEBUG_AddUser() {
        viewModelScope.launch { userRepo.addUser(User("Paula", "ABCDFGHI")) }
    }


    // TODO Get User by friend code, create group and add current user id and friend user id as members
    fun addFriend(code: String) {

        viewModelScope.launch {
            val friend = userRepo.getUserByFriendCode(code)
            val currentUser = userRepo.getCurrentUser()
            if (friend != null && currentUser != null) {
                expensesRepo.addGroup(
                    Group(
                        friend.name,
                        listOf(currentUser.name, friend.name)
                    )
                )
            }
        }
    }
}