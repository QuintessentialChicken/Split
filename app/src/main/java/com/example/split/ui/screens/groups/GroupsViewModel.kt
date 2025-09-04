package com.example.split.ui.screens.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.ExpensesRepository
import com.example.split.data.Group
import com.example.split.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val expensesRepo: ExpensesRepository,
    private val userRepo: UsersRepository
) : ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    fun loadGroups(userId: String) {
        println("REFRESHING GROUPS")
        viewModelScope.launch {
            val result = expensesRepo.getGroupsByUserId(userId)
            _groups.value = result
        }.invokeOnCompletion {
            println(_groups.value)
        }
    }
}