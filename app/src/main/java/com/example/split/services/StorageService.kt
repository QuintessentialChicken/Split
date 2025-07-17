package com.example.split.services

import com.example.split.data.Expense
import kotlinx.coroutines.flow.Flow

interface StorageService {
    suspend fun addExpense(expense: Expense)
    fun getExpenses(): Flow<List<Expense>>
    suspend fun deleteExpense(id: String)
    suspend fun updateExpense(expense: Expense)
}