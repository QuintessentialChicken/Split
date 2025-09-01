package com.example.split.ui.screens.friends

import androidx.lifecycle.ViewModel
import com.example.split.data.ExpensesRepository
import com.example.split.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
private val expensesRepo: ExpensesRepository,
private val userRepo: UsersRepository
) : ViewModel() {

}