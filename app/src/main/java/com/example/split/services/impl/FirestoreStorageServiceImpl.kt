package com.example.split.services.impl

import com.example.split.data.Expense
import com.example.split.data.ExpenseParticipant
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
    firestore: FirebaseFirestore,
): StorageService {
    private val usersRef: CollectionReference = firestore.collection("users")

    override suspend fun addUser(user: User) {
        usersRef.document(auth.currentUserId).set(user)
    }

    override fun getAllUsers(): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun addExpense(
        expense: Expense,
        participants: List<Participant>
    ) {
        TODO("Not yet implemented")
    }

    override fun getExpenses(): Flow<List<Expense>> {
        TODO("Not yet implemented")
    }

    override fun getExpensesSortedByDateDesc(): Flow<List<Expense>> {
        TODO("Not yet implemented")
    }

    override fun getAllParticipants(): Flow<List<ExpenseParticipant>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(expense: Expense) {
        TODO("Not yet implemented")
    }
}