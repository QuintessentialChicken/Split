package com.example.split.data

import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpensesRepository @Inject constructor(
    private val local: StorageService
) {
    suspend fun addExpense(expense: Expense) {
        local.addExpense(expense)
    }

    fun getAllExpenses(): Flow<List<Expense>> {
        return local.getExpenses()
    }
}