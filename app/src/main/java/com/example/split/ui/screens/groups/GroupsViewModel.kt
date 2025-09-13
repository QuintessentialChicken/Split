package com.example.split.ui.screens.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.split.data.ExpensesRepository
import com.example.split.data.Group
import com.example.split.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val expensesRepo: ExpensesRepository,
    private val userRepo: UsersRepository
) : ViewModel() {

    val groups: StateFlow<List<Group>> = expensesRepo.getGroupsFlow("A2zZCcXnbNy1iQVdcKA8", false).stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

}