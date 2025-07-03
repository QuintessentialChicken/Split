package com.example.split.data

import com.example.split.services.StorageService
import com.example.split.services.impl.RoomStorageServiceImpl
import javax.inject.Inject

class ExpensesRepository @Inject constructor(
    private val local: StorageService
) {
    suspend fun addExpense(expense: Expense) {
        local.addExpense(expense)
    }

    suspend fun getAllExpenses(): List<Expense> {
        return local.getExpenses()
    }
}