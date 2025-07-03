package com.example.split.services

import com.example.split.data.Expense

interface StorageService {
    suspend fun addExpense(expense: Expense)
    suspend fun getExpenses(): List<Expense>
    suspend fun deleteExpense(id: String)
    suspend fun updateExpense(expense: Expense)
}