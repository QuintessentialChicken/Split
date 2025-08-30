package com.example.split.services.impl

import com.example.split.data.Expense
import com.example.split.data.ExpenseDao
import com.example.split.data.ExpenseParticipant
import com.example.split.data.FirestoreGroup
import com.example.split.data.Group
import com.example.split.data.Participant
import com.example.split.data.ParticipantDao
import com.example.split.data.User
import com.example.split.data.UserDao
import com.example.split.services.StorageService
import kotlinx.coroutines.flow.Flow

class RoomStorageServiceImpl(private val expenseDao: ExpenseDao, private val userDao: UserDao, private val participantDao: ParticipantDao) : StorageService {
    override suspend fun addGroup(group: FirestoreGroup) {
        TODO("Not yet implemented")
    }

    override suspend fun addExpenseToGroup(
        expense: Expense,
        groupId: String
    ) {
        TODO("Not yet implemented")
    }

    override fun getExpensesByGroup(groupId: String): Flow<List<Expense>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(expense: Expense) {
        TODO("Not yet implemented")
    }

}