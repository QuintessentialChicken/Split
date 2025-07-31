package com.example.split.services

import com.example.split.data.Expense
import com.example.split.data.ExpenseParticipant
import com.example.split.data.Participant
import com.example.split.data.User
import kotlinx.coroutines.flow.Flow

interface StorageService {
    suspend fun addUser(user: User)
    fun getAllUsers(): Flow<List<User>>
    suspend fun addExpense(expense: Expense, participants: List<Participant>)
    fun getExpenses(): Flow<List<Expense>>
    fun getExpensesSortedByDateDesc(): Flow<List<Expense>>
    fun getAllParticipants(): Flow<List<ExpenseParticipant>>
    suspend fun deleteExpense(id: String)
    suspend fun updateExpense(expense: Expense)
}