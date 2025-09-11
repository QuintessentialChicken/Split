package com.example.split.services

import com.example.split.data.Expense
import com.example.split.data.Group
import com.example.split.data.User
import kotlinx.coroutines.flow.Flow

interface StorageService {
    suspend fun addUser(id: String, user: User)

    suspend fun getUserById(id: String): User?

    suspend fun getUserByFriendCode(code: String): User?
    suspend fun addGroup(group: Group)

    suspend fun addExpenseToGroup(expense: Expense, groupId: String)

    suspend fun getGroupsByUserId(id: String): List<Group>
    suspend fun getFriendsByUserId(id: String): List<Group>
    fun getExpensesFlow(groupId: String): Flow<List<Expense>>
    suspend fun getExpensesCashed(groupId: String): List<Expense>
    suspend fun deleteExpense(id: String)
    suspend fun updateExpense(expense: Expense)
}