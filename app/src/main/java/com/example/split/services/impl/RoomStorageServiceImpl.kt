package com.example.split.services.impl

import com.example.split.data.Expense
import com.example.split.data.ExpenseDao
import com.example.split.services.StorageService

class RoomStorageServiceImpl(private val dao: ExpenseDao) : StorageService {
    override suspend fun addExpense(expense: Expense) = dao.insert(expense)
    override suspend fun getExpenses(): List<Expense> = dao.getAll()
    override suspend fun deleteExpense(id: String) = dao.delete(id)
    override suspend fun updateExpense(expense: Expense) = dao.update(expense)
}