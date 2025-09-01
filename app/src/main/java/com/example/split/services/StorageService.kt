package com.example.split.services

import com.example.split.data.Expense
import com.example.split.data.Group
import kotlinx.coroutines.flow.Flow

interface StorageService {
    suspend fun addGroup(group: Group)
    suspend fun addExpenseToGroup(expense: Expense, groupId: String)
    fun getExpensesByGroup(groupId: String): Flow<List<Expense>>
    suspend fun deleteExpense(id: String)
    suspend fun updateExpense(expense: Expense)
}