package com.example.split.services.impl

import com.example.split.data.Expense
import com.example.split.data.ExpenseDao
import com.example.split.data.User
import com.example.split.data.UserDao
import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow

// TODO Suspend?
class RoomStorageServiceImpl(private val expenseDao: ExpenseDao, private val userDao: UserDao) : StorageService {
    override suspend fun addUser(user: User) = userDao.insert(user)
    override suspend fun addExpense(expense: Expense) = expenseDao.insert(expense)
    override fun getExpenses(): Flow<List<Expense>> = expenseDao.getAll()
    override suspend fun deleteExpense(id: String) = expenseDao.delete(id)
    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
}