package com.example.split.data

import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpensesRepository @Inject constructor(
    private val firestore: StorageService
) {
    fun getAll(): Flow<List<Expense>> {
        return firestore.getExpenses()
    }

    fun getAllSortedByDateDesc(): Flow<List<Expense>> {
        return firestore.getExpensesSortedByDateDesc()
    }

    fun getAllParticipants(): Flow<List<ExpenseParticipant>> {
        return firestore.getAllParticipants()
    }

    suspend fun addExpense(expense: Expense, participants: List<Participant>) {
        firestore.addExpense(expense, participants)
    }
}

class UsersRepository @Inject constructor(
    private val firestore: StorageService
) {
    fun getAll(): Flow<List<User>> {
        return firestore.getAllUsers()
    }

    suspend fun addUser(user: User) {
        firestore.addUser(user)
    }
}