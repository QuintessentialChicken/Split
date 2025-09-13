package com.example.split.data

import com.example.split.services.AccountService
import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpensesRepository @Inject constructor(
    private val firestore: StorageService
) {
    suspend fun addGroup(group: Group) = firestore.addGroup(group)
    suspend fun addExpenseToGroup(expense: Expense, groupId: String) = firestore.addExpenseToGroup(expense, groupId)

    suspend fun getGroupsByUserId(id: String) = firestore.getGroupsByUserId(id)

    fun getGroupsFlow(id: String, isFriend: Boolean) = firestore.getGroupsFlow(id, isFriend)
    suspend fun getFriendsByUserId(id: String) = firestore.getFriendsByUserId(id)
    fun getExpensesByGroup(groupId: String): Flow<List<Expense>> = firestore.getExpensesFlow(groupId)
    suspend fun deleteExpense(id: String) = firestore.deleteExpense(id)
    suspend fun updateExpense(expense: Expense) = firestore.updateExpense(expense)
}

class UsersRepository @Inject constructor(
    private val auth: AccountService,
    private val firestore: StorageService
) {
    fun getCurrentUserId(): String = auth.currentUserId
    suspend fun addUser(user: User) = firestore.addUser(auth.currentUserId, user)
    suspend fun getCurrentUser(): User? = firestore.getUserById("2")
    suspend fun getUserById(id: String): User? = firestore.getUserById(id)
    suspend fun getUserByFriendCode(code: String): User? = firestore.getUserByFriendCode(code)
}