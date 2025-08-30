package com.example.split.data

import com.example.split.services.AccountService
import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpensesRepository @Inject constructor(
    private val firestore: StorageService
) {
    suspend fun addGroup(group: FirestoreGroup) = firestore.addGroup(group)
    suspend fun addExpenseToGroup(expense: Expense, groupId: String) = firestore.addExpenseToGroup(expense, groupId)
    fun getExpensesByGroup(groupId: String): Flow<List<Expense>> = firestore.getExpensesByGroup(groupId)
    suspend fun deleteExpense(id: String) = firestore.deleteExpense(id)
    suspend fun updateExpense(expense: Expense) = firestore.updateExpense(expense)
}

class UsersRepository @Inject constructor(
    private val auth: AccountService
) {

}