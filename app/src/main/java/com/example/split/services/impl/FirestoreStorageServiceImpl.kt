package com.example.split.services.impl

import com.example.split.data.Expense
import com.example.split.data.Group
import com.example.split.data.User
import com.example.split.services.AccountService
import com.example.split.services.StorageService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreStorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val firestore: FirebaseFirestore,
): StorageService {
    private val groupsRef: CollectionReference = firestore.collection("groups")
    private val usersRef: CollectionReference = firestore.collection("users")

    override suspend fun addUser(id: String, user: User) {
        usersRef.document("1").set(user)
    }

    override suspend fun getUserById(id: String): User? {
        if (id == "") {
            println("ASDASDAS")
            return null
        }
        val result = usersRef.document(id).get().await()
        return result.toObject<User>()
    }

    override suspend fun getUserByFriendCode(code: String): User? {
        val users = usersRef
            .whereEqualTo("friendCode", code)
            .get()
            .await()
        return if (users.isEmpty) null else users.first().toObject<User>()
    }

    override suspend fun addGroup(group: Group) {
        groupsRef.document().set(group)
    }

    override suspend fun addExpenseToGroup(
        expense: Expense,
        groupId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupsByUserId(id: String): List<Group> {
        val groups = groupsRef
            .whereArrayContains("members", id)
            .get()
            .await()
        return groups.toObjects<Group>()
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