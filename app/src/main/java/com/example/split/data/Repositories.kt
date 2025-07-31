package com.example.split.data

import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpensesRepository @Inject constructor(
    private val local: StorageService
) {
    fun getAll(): Flow<List<Expense>> {
        return local.getExpenses()
    }

    fun getAllSortedByDateDesc(): Flow<List<Expense>> {
        return local.getExpensesSortedByDateDesc()
    }

    fun getAllParticipants(): Flow<List<ExpenseParticipant>> {
        return local.getAllParticipants()
    }

    suspend fun addExpense(expense: Expense, participants: List<Participant>) {
        local.addExpense(expense, participants)
    }
}

class UsersRepository @Inject constructor(
    private val local: StorageService
) {
    fun getAll(): Flow<List<User>> {
        return local.getAllUsers()
    }

    suspend fun addUser(user: User) {
        local.addUser(user)
    }
}