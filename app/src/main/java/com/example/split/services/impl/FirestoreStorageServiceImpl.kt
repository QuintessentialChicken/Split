package com.example.split.services.impl

import com.example.split.data.Expense
import com.example.split.data.ExpenseParticipant
import com.example.split.data.FirestoreGroup
import com.example.split.data.Group
import com.example.split.data.Participant
import com.example.split.data.User
import com.example.split.services.AccountService
import com.example.split.services.StorageService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreStorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val firestore: FirebaseFirestore,
): StorageService {
    private val groupsRef: CollectionReference = firestore.collection("groups")
    override suspend fun addGroup(group: FirestoreGroup) {
        groupsRef.document().set(group)
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